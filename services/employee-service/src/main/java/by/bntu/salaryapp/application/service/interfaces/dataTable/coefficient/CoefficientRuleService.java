package by.bntu.salaryapp.application.service.interfaces.dataTable.coefficient;

import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficientRule.CoefficientRuleDto;

import java.util.List;
import java.util.UUID;

public interface CoefficientRuleService {
    CoefficientRuleDto findById(UUID id);

    List<CoefficientRuleDto> findAllByCoefficientId(UUID coefficientId);

    CoefficientRuleDto create(CoefficientRuleDto dto);

    CoefficientRuleDto update(CoefficientRuleDto dto);

    void delete(UUID id);
}
