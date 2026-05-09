package by.bntu.salaryapp.infrastructure.mapper.dataTable.coefficient.coefficientRule;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficientRule.CoefficientRuleDto;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.CoefficientRule;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = CoefficientRuleMapper.class)
public interface CoefficientRuleListMapper {
    List<CoefficientRuleDto> toDtoList(List<CoefficientRule> rules);

    List<CoefficientRule> toEntityList(List<CoefficientRuleDto> dtos);
}
