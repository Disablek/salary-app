package by.bntu.salaryapp.application.service.implementations.dataTable.coefficient;

import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficient.CoefficientDto;
import by.bntu.salaryapp.application.service.interfaces.dataTable.coefficient.CoefficientService;
import by.bntu.salaryapp.domain.common.enums.CoefficientType;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.CoefficientRule;
import by.bntu.salaryapp.infrastructure.mapper.dataTable.coefficient.coefficient.CoefficientListMapper;
import by.bntu.salaryapp.infrastructure.mapper.dataTable.coefficient.coefficient.CoefficientMapper;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.coefficient.CoefficientRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.coefficient.CoefficientRuleRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.ColumnRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoefficientServiceImpl implements CoefficientService {

    private final CoefficientRepository coefficientRepository;
    private final CoefficientRuleRepository coefficientRuleRepository;
    private final ColumnRepository columnRepository;
    private final CoefficientMapper coefficientMapper;
    private final CoefficientListMapper coefficientListMapper;

    @Override
    public CoefficientDto findById(UUID id) {
        Coefficient coefficient = coefficientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coefficient not found with id: " + id));
        return coefficientMapper.toDto(coefficient);
    }

    @Override
    public List<CoefficientDto> findAllActive() {
        List<Coefficient> activeCoefficients = coefficientRepository.findAllByIsActiveTrue();
        return coefficientListMapper.toDtoList(activeCoefficients);
    }

    @Override
    public List<CoefficientDto> findAllByCoefficientType(CoefficientType coefficientType) {
        List<Coefficient> coefficients = coefficientRepository.findAllByType(coefficientType);
        return coefficientListMapper.toDtoList(coefficients);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public CoefficientDto create(CoefficientDto dto) {
        String title = dto.getTitle().trim();

        if (coefficientRepository.existsByTitle(title)) {
            throw new IllegalArgumentException("Coefficient with title '" + title + "' already exists");
        }

        Coefficient coefficient = coefficientMapper.toEntity(dto);

        Column targetColumn = columnRepository.findById(dto.getTargetColumnId())
                .orElseThrow(() -> new EntityNotFoundException("Target Column not found with id: " + dto.getTargetColumnId()));
        coefficient.setTargetColumn(targetColumn);

        Column sourceColumn = columnRepository.findById(dto.getSourceColumnId())
                .orElseThrow(() -> new EntityNotFoundException("Source Column not found with id: " + dto.getSourceColumnId()));
        coefficient.setSourceColumn(sourceColumn);

        Column baseColumn = columnRepository.findById(dto.getBaseColumnId())
                .orElseThrow(() -> new EntityNotFoundException("Base Column not found with id: " + dto.getBaseColumnId()));
        coefficient.setBaseColumn(baseColumn);

        assignRulesToCoefficient(coefficient, dto.getCoefficientRulesIds());

        Coefficient savedCoefficient = coefficientRepository.save(coefficient);
        return coefficientMapper.toDto(savedCoefficient);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public CoefficientDto update(CoefficientDto dto) {
        Coefficient existingCoefficient = coefficientRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Coefficient not found with id: " + dto.getId()));

        String newTitle = dto.getTitle().trim();

        if (!existingCoefficient.getTitle().equals(newTitle) && coefficientRepository.existsByTitle(newTitle)) {
            throw new IllegalArgumentException("Coefficient with title '" + newTitle + "' already exists");
        }

        coefficientMapper.updateFromDto(dto, existingCoefficient);

        Column targetColumn = columnRepository.findById(dto.getTargetColumnId())
                .orElseThrow(() -> new EntityNotFoundException("Target Column not found with id: " + dto.getTargetColumnId()));
        existingCoefficient.setTargetColumn(targetColumn);

        Column sourceColumn = columnRepository.findById(dto.getSourceColumnId())
                .orElseThrow(() -> new EntityNotFoundException("Source Column not found with id: " + dto.getSourceColumnId()));
        existingCoefficient.setSourceColumn(sourceColumn);

        Column baseColumn = columnRepository.findById(dto.getBaseColumnId())
                .orElseThrow(() -> new EntityNotFoundException("Base Column not found with id: " + dto.getBaseColumnId()));
        existingCoefficient.setBaseColumn(baseColumn);

        if (dto.getCoefficientRulesIds() != null) {
            assignRulesToCoefficient(existingCoefficient, dto.getCoefficientRulesIds());
        }

        Coefficient savedCoefficient = coefficientRepository.save(existingCoefficient);
        return coefficientMapper.toDto(savedCoefficient);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public void delete(UUID id) {
        if (!coefficientRepository.existsById(id)) {
            throw new EntityNotFoundException("Coefficient not found with id: " + id);
        }
        coefficientRepository.deleteById(id);
    }

    private void assignRulesToCoefficient(Coefficient coefficient, Set<UUID> ruleIds) {
        coefficient.getCoefficientRules().clear();

        if (ruleIds != null && !ruleIds.isEmpty()) {
            List<CoefficientRule> rules = coefficientRuleRepository.findAllById(ruleIds);

            if (rules.size() != ruleIds.size()) {
                throw new EntityNotFoundException("One or more CoefficientRules not found");
            }

            rules.forEach(rule -> rule.setCoefficient(coefficient));

            coefficient.getCoefficientRules().addAll(rules);
        }
    }
}