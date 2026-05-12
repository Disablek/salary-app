package by.bntu.salaryapp.infrastructure.mapper.dataTable.coefficient.coefficientRule;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficientRule.CoefficientRuleDto;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.CoefficientRule;
import org.mapstruct.*;

@Mapper(config = MapStructConfig.class)
public interface CoefficientRuleMapper {
    @Mapping(source = "coefficient.id", target = "coefficientId")
    @Mapping(target = "createdBy", expression = "java(entity.getCreatedBy() != null ? entity.getCreatedBy().getId() : null)")
    @Mapping(target = "updatedBy", expression = "java(entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null)")
    @Mapping(target = "createdAt", expression = "java(entity.getCreatedDate())")
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
