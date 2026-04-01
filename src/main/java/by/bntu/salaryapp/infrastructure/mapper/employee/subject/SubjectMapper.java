package by.bntu.salaryapp.infrastructure.mapper.employee.subject;

import by.bntu.salaryapp.application.dto.employee.subject.SubjectDto;
import by.bntu.salaryapp.domain.model.employee.Subject;
import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import org.mapstruct.*;

@Mapper(config = MapStructConfig.class)
public interface SubjectMapper {

    // Subject entity has only id and title (no auditing fields), map only existing properties
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SubjectDto toDto(Subject subject);

    Subject toEntity(SubjectDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(SubjectDto dto, @MappingTarget Subject entity);
}