package by.bntu.salaryapp.infrastructure.mapper.employee.qualification;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.employee.qualification.QualificationDto;
import by.bntu.salaryapp.domain.model.employee.Qualification;
import org.mapstruct.*;

@Mapper(config = MapStructConfig.class)
public interface QualificationMapper {
    @Mapping(target = "createdBy", expression = "java(qualification.getCreatedBy() != null ? qualification.getCreatedBy().getId() : null)")
    @Mapping(target = "updatedBy", expression = "java(qualification.getUpdatedBy() != null ? qualification.getUpdatedBy().getId() : null)")
    @Mapping(target = "createdAt", expression = "java(qualification.getCreatedDate())")
    QualificationDto toDto(Qualification qualification);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Qualification toEntity(QualificationDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(QualificationDto dto, @MappingTarget Qualification entity);
}
