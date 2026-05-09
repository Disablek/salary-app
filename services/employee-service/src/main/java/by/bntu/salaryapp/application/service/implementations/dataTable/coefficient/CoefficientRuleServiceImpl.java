package by.bntu.salaryapp.application.service.implementations.dataTable.coefficient;

import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficientRule.CoefficientRuleDto;
import by.bntu.salaryapp.application.service.interfaces.dataTable.coefficient.CoefficientRuleService;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.CoefficientRule;
import by.bntu.salaryapp.infrastructure.mapper.dataTable.coefficient.coefficientRule.CoefficientRuleListMapper;
import by.bntu.salaryapp.infrastructure.mapper.dataTable.coefficient.coefficientRule.CoefficientRuleMapper;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.coefficient.CoefficientRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.coefficient.CoefficientRuleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoefficientRuleServiceImpl implements CoefficientRuleService {

    private final CoefficientRuleRepository coefficientRuleRepository;
    private final CoefficientRepository coefficientRepository;

    private final CoefficientRuleMapper coefficientRuleMapper;
    private final CoefficientRuleListMapper coefficientRuleListMapper;

    @Override
    public CoefficientRuleDto findById(UUID id) {
        CoefficientRule rule = coefficientRuleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CoefficientRule not found with id: " + id));
        return coefficientRuleMapper.toDto(rule);
    }

    @Override
    public List<CoefficientRuleDto> findAllByCoefficientId(UUID coefficientId) {
        if (!coefficientRepository.existsById(coefficientId)) {
            throw new EntityNotFoundException("Coefficient not found with id: " + coefficientId);
        }
        List<CoefficientRule> rules = coefficientRuleRepository.findAllByCoefficientId(coefficientId);
        return coefficientRuleListMapper.toDtoList(rules);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public CoefficientRuleDto create(CoefficientRuleDto dto) {
        Coefficient coefficient = coefficientRepository.findById(dto.getCoefficientId())
                .orElseThrow(() -> new EntityNotFoundException("Coefficient not found with id: " + dto.getCoefficientId()));

        CoefficientRule rule = coefficientRuleMapper.toEntity(dto);
        rule.setCoefficient(coefficient);

        if (rule.getMatchValue() == null) {
            rule.setMatchValue("");
        }

        CoefficientRule savedRule = coefficientRuleRepository.save(rule);
        return coefficientRuleMapper.toDto(savedRule);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public CoefficientRuleDto update(CoefficientRuleDto dto) {
        CoefficientRule existingRule = coefficientRuleRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("CoefficientRule not found with id: " + dto.getId()));

        if (dto.getCoefficientId() != null && !existingRule.getCoefficient().getId().equals(dto.getCoefficientId())) {
            Coefficient newCoefficient = coefficientRepository.findById(dto.getCoefficientId())
                    .orElseThrow(() -> new EntityNotFoundException("Coefficient not found with id: " + dto.getCoefficientId()));
            existingRule.setCoefficient(newCoefficient);
        }

        coefficientRuleMapper.updateFromDto(dto, existingRule);

        if (existingRule.getMatchValue() == null) {
            existingRule.setMatchValue("");
        }

        CoefficientRule savedRule = coefficientRuleRepository.save(existingRule);
        return coefficientRuleMapper.toDto(savedRule);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public void delete(UUID id) {
        if (!coefficientRuleRepository.existsById(id)) {
            throw new EntityNotFoundException("CoefficientRule not found with id: " + id);
        }
        coefficientRuleRepository.deleteById(id);
    }
}