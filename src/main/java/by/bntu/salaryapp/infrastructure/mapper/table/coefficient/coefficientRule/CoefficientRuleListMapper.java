package by.bntu.salaryapp.infrastructure.mapper.table.coefficient.coefficientRule;

import by.bntu.salaryapp.application.dto.table.coefficient.coefficientRule.CoefficientRuleDto;
import by.bntu.salaryapp.domain.model.table.coefficient.CoefficientRule;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CoefficientRuleMapper.class)
public interface CoefficientRuleListMapper {
    List<CoefficientRuleDto> toDtoList(List<CoefficientRule> rules);

    List<CoefficientRule> toEntityList(List<CoefficientRuleDto> dtos);
}
