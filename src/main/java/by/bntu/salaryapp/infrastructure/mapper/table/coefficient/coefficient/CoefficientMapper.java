package by.bntu.salaryapp.infrastructure.mapper.table.coefficient.coefficient;

import by.bntu.salaryapp.application.dto.table.coefficient.coefficient.CoefficientDto;
import by.bntu.salaryapp.domain.model.BaseAuditingEntity;
import by.bntu.salaryapp.domain.model.table.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.table.coefficient.CoefficientRule;
import org.mapstruct.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CoefficientMapper {
    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(source = "updatedBy.id", target = "updatedBy")
    @Mapping(source = "createdDate", target = "createdAt")
    @Mapping(target = "coefficientRulesIds", expression = "java(mapRulesToIds(coefficient.getCoefficientRules()))")
    CoefficientDto toDto(Coefficient coefficient);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "coefficientRules", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Coefficient toEntity(CoefficientDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "coefficientRules", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(CoefficientDto dto, @MappingTarget Coefficient entity);

    default Set<UUID> mapRulesToIds(Set<CoefficientRule> rules) {
        if (rules == null) {
            return Set.of();
        }
        return rules.stream()
                .map(BaseAuditingEntity::getId)
                .collect(Collectors.toSet());
    }
}

