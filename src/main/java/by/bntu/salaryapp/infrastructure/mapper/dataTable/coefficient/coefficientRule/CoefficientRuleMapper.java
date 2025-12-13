package by.bntu.salaryapp.infrastructure.mapper.dataTable.coefficient.coefficientRule;

import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficientRule.CoefficientRuleDto;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.CoefficientRule;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CoefficientRuleMapper {
    @Mapping(source = "coefficient.id", target = "coefficientId")
    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(source = "updatedBy.id", target = "updatedBy")
    @Mapping(source = "createdDate", target = "createdAt")
    CoefficientRuleDto toDto(CoefficientRule entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "coefficient", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CoefficientRule toEntity(CoefficientRuleDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "coefficient", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(CoefficientRuleDto dto, @MappingTarget CoefficientRule entity);
}
