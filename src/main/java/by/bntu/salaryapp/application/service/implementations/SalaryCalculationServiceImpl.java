package by.bntu.salaryapp.application.service.implementations;


import by.bntu.salaryapp.application.service.interfaces.SalaryCalculationService;
import by.bntu.salaryapp.domain.model.dataTable.Cell;
import by.bntu.salaryapp.domain.model.dataTable.Row;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.CoefficientRule;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.CellRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.coefficient.CoefficientRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.RowRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryCalculationServiceImpl implements SalaryCalculationService {

    private final RowRepository rowRepository;
    private final CoefficientRepository coefficientRepository;
    private final CellRepository cellRepository;

    @Override
    @Transactional
    public void calculateRow(UUID rowId) {
        Row row = rowRepository.findById(rowId)
                .orElseThrow(() -> new EntityNotFoundException("Row not found: " + rowId));

        UUID tableId = row.getTable().getId();

        List<Coefficient> coefficients = coefficientRepository.findActiveByTableId(tableId);

        if (coefficients.isEmpty()) {
            return;
        }

        boolean rowUpdated = false;

        for (Coefficient coefficient : coefficients) {
            try {
                processCoefficient(row, coefficient);
                rowUpdated = true;
            } catch (Exception e) {
                log.error("Error calculating coefficient '{}' for row '{}': {}",
                        coefficient.getTitle(), rowId, e.getMessage());
                //TODO: Exception
                // Можно выбрасывать исключение дальше или писать в лог ошибку в специальную ячейку
            }
        }

        if (rowUpdated) {
            rowRepository.save(row);
        }
    }

    @Override
    @Transactional
    public void calculateTable(UUID tableId) {
        // В реальном приложении лучше использовать batch processing или async,
        // если строк тысячи. Здесь простая реализация.
        List<Row> rows = rowRepository.findAllByTableId(tableId);
        for (Row row : rows) {
            calculateRow(row.getId());
        }
    }

    /** Логика расчёта ячейки
     * А. Находим сравниваемый столбец
     * Б. Ищем подходящий множитель в нём
     * В. Находим исходный столбец
     * Г. Вычисляем результат
     */
    private void processCoefficient(Row row, Coefficient coefficient) {
        // Source Column
        String sourceValue = getCellValue(row, coefficient.getSourceColumn().getId());
        if (sourceValue == null) sourceValue = "";

        // Ищем подходящий множитель
        BigDecimal multiplier = findMultiplier(coefficient, sourceValue);

        if (multiplier == null) {
            log.debug("No matching rule found for value '{}' in coefficient '{}'", sourceValue, coefficient.getTitle());
            updateTargetCell(row, coefficient, "0");
            return;
        }

        // Base Column (число)
        String baseValueStr = getCellValue(row, coefficient.getBaseColumn().getId());
        BigDecimal baseValue = BigDecimal.ZERO;

        try {
            if (baseValueStr != null && !baseValueStr.isBlank()) {
                baseValue = new BigDecimal(baseValueStr);
            }
        } catch (NumberFormatException e) {
            log.warn("Invalid number format in base column for row {}: {}", row.getId(), baseValueStr);
        }

        BigDecimal result = baseValue.multiply(multiplier);

        updateTargetCell(row, coefficient, result.toString());
    }

    /**
     * Логика поиска правила:
     * 1. Точное совпадение.
     * 2. Пустая строка
     */
    private BigDecimal findMultiplier(Coefficient coefficient, String value) {
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

    private String getCellValue(Row row, UUID columnId) {
        return row.getCells().stream()
                .filter(c -> c.getColumn().getId().equals(columnId))
                .findFirst()
                .map(Cell::getValue)
                .orElse(null);
    }

    private void updateTargetCell(Row row, Coefficient coefficient, String newValue) {
        UUID targetColumnId = coefficient.getTargetColumn().getId();

        // Ищем существующую ячейку
        Optional<Cell> existingCell = row.getCells().stream()
                .filter(c -> c.getColumn().getId().equals(targetColumnId))
                .findFirst();

        if (existingCell.isPresent()) {
            existingCell.get().setValue(newValue);
        } else {
            // Если ячейки нет - создаем новую
            Cell newCell = Cell.builder()
                    .row(row)
                    .column(coefficient.getTargetColumn())
                    .value(newValue)
                    .build();

            // Важно добавить в обе стороны связи
            row.getCells().add(newCell);
            // newCell сохраняется каскадно при сохранении Row
        }
    }
}
