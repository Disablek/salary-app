package by.bntu.salaryapp.application.service.implementations;

import by.bntu.salaryapp.application.service.interfaces.SalaryCalculationService;
import by.bntu.salaryapp.domain.common.enums.CoefficientType;
import by.bntu.salaryapp.domain.model.dataTable.Cell;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.Row;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.CoefficientRule;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.CellRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.RowRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.coefficient.CoefficientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryCalculationServiceImpl implements SalaryCalculationService {

    private final RowRepository rowRepository;
    private final CoefficientRepository coefficientRepository;
    private final CellRepository cellRepository; // на случай, если понадобится явное сохранение

    @Override
    @Transactional
    public void calculateRow(UUID rowId) {
        Row row = rowRepository.findById(rowId)
                .orElseThrow(() -> new EntityNotFoundException("Row not found with id: " + rowId));

        UUID tableId = row.getTable().getId();
        List<Coefficient> coefficients = coefficientRepository.findActiveByTableId(tableId);

        for (Coefficient coeff : coefficients) {
            try {
                if (coeff.getType() == CoefficientType.MULTIPLIER) {
                    processCoefficient(row, coeff);
                } else if (coeff.getType() == CoefficientType.SUMMATION) {
                    processSummation(row, coeff);
                } else {
                    log.debug("Unknown coefficient type {} for coefficient {}", coeff.getType(), coeff.getId());
                }
            } catch (Exception e) {
                log.error("Error processing coefficient {} for row {}: {}", coeff.getId(), rowId, e.getMessage(), e);
            }
        }

        // Сохраняем row — каскад сохранит новые/изменённые клетки
        rowRepository.save(row);
    }

    @Override
    @Transactional
    public void calculateTable(UUID tableId) {
        // Если таблиц много — лучше батчить/параллелить, но пока простой последовательный проход
        List<Row> rows = rowRepository.findAllByTableId(tableId);
        for (Row row : rows) {
            calculateRow(row.getId());
        }
    }

    /**
     * MULTIPLIER logic (generalized)
     * 1) Получаем source value (строка) по sourceColumn
     * 2) Ищем подходящее правило (exact match или default)
     * 3) Берём baseColumn как число, умножаем на multiplier
     * 4) Записываем результат в targetColumn
     */
    private void processCoefficient(Row row, Coefficient coefficient) {
        // Source Column (сравниваемое значение)
        String sourceValue = getCellValue(row, coefficient.getSourceColumn().getId());
        if (sourceValue == null) sourceValue = "";

        // Ищем подходящий множитель
        BigDecimal multiplier = findMultiplier(coefficient, sourceValue);

        if (multiplier == null) {
            log.debug("No matching rule found for value '{}' in coefficient '{}'", sourceValue, coefficient.getTitle());
            updateTargetCell(row, coefficient, BigDecimal.ZERO.toString());
            return;
        }

        // Base Column (число)
        String baseValueStr = getCellValue(row, coefficient.getBaseColumn().getId());
        BigDecimal baseValue = parseBigDecimalSafe(baseValueStr);

        BigDecimal result = baseValue.multiply(multiplier);

        updateTargetCell(row, coefficient, result.toString());
    }

    /**
     * SUMMATION logic
     * Суммируем значения в списке колонок coefficient.getSummationColumns()
     * и записываем в targetColumn.
     */
    private void processSummation(Row row, Coefficient coeff) {
        BigDecimal sum = BigDecimal.ZERO;

        // ИСПРАВЛЕНИЕ: меняем List на Set
        Set<Column> summationColumns = coeff.getSummationColumns();

        if (summationColumns == null || summationColumns.isEmpty()) {
            log.debug("Summation coefficient {} has no summation columns", coeff.getId());
            updateTargetCell(row, coeff, BigDecimal.ZERO.toString());
            return;
        }

        for (Column col : summationColumns) {
            String valStr = getCellValue(row, col.getId());
            if (valStr != null) {
                // Убедитесь, что у вас есть этот вспомогательный метод parseBigDecimalSafe
                // или используйте try-catch с new BigDecimal(valStr)
                BigDecimal parsed = parseBigDecimalSafe(valStr);
                sum = sum.add(parsed);
            }
        }

        updateTargetCell(row, coeff, sum.toString());
    }

    /**
     * Поиск множителя в правилах коэффициента.
     * Сначала exact match (String equals), затем default rule (matchValue null or blank).
     */
    private BigDecimal findMultiplier(Coefficient coefficient, String value) {
        if (coefficient.getCoefficientRules() == null || coefficient.getCoefficientRules().isEmpty()) {
            return null;
        }

        Optional<CoefficientRule> exactMatch = coefficient.getCoefficientRules().stream()
                .filter(rule -> Objects.equals(rule.getMatchValue(), value))
                .findFirst();

        if (exactMatch.isPresent()) {
            return exactMatch.get().getMultiplier();
        }

        Optional<CoefficientRule> defaultMatch = coefficient.getCoefficientRules().stream()
                .filter(rule -> rule.getMatchValue() == null || rule.getMatchValue().trim().isEmpty())
                .findFirst();

        return defaultMatch.map(CoefficientRule::getMultiplier).orElse(null);
    }

    /**
     * Получить значение ячейки в строке по columnId.
     * Возвращает raw string или null.
     */
    private String getCellValue(Row row, UUID columnId) {
        if (row.getCells() == null || columnId == null) return null;

        return row.getCells().stream()
                .filter(c -> c.getColumn() != null && columnId.equals(c.getColumn().getId()))
                .findFirst()
                .map(Cell::getValue)
                .orElse(null);
    }

    /**
     * Вставить/обновить значение в целевой колонке.
     * Если cell существует — обновляем, иначе создаём новую и добавляем в row.cells.
     */
    private void updateTargetCell(Row row, Coefficient coefficient, String newValue) {
        if (coefficient.getTargetColumn() == null) {
            log.warn("Coefficient {} has no target column configured", coefficient.getId());
            return;
        }

        UUID targetColumnId = coefficient.getTargetColumn().getId();

        Optional<Cell> existingCell = row.getCells().stream()
                .filter(c -> c.getColumn() != null && targetColumnId.equals(c.getColumn().getId()))
                .findFirst();

        if (existingCell.isPresent()) {
            existingCell.get().setValue(newValue);
        } else {
            Cell newCell = Cell.builder()
                    .row(row)
                    .column(coefficient.getTargetColumn())
                    .value(newValue)
                    .build();

            // поддерживаем двунаправленную связь
            row.getCells().add(newCell);
        }
    }

    /**
     * Парсинг BigDecimal с заменой запятой на точку, удалением пробелов, безопасно — при ошибке возвращает ZERO.
     */
    private BigDecimal parseBigDecimalSafe(String raw) {
        if (raw == null) return BigDecimal.ZERO;
        try {
            String clean = raw.replace(",", ".").replaceAll("\\s+", "");
            if (clean.isEmpty()) return BigDecimal.ZERO;
            return new BigDecimal(clean);
        } catch (NumberFormatException e) {
            log.warn("Failed to parse BigDecimal from '{}', defaulting to 0", raw);
            return BigDecimal.ZERO;
        }
    }

}
