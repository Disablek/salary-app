package by.bntu.salaryapp.infrastructure.mapper.employee.experience;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.employee.experience.ExperienceDto;
import by.bntu.salaryapp.domain.model.employee.Experience;
import org.mapstruct.*;

@Mapper(config = MapStructConfig.class)
public interface ExperienceMapper {
    @Mapping(target = "createdBy", expression = "java(experience.getCreatedBy() != null ? experience.getCreatedBy().getId() : null)")
    @Mapping(target = "updatedBy", expression = "java(experience.getUpdatedBy() != null ? experience.getUpdatedBy().getId() : null)")
    @Mapping(target = "createdAt", expression = "java(experience.getCreatedDate())")
    ExperienceDto toDto(Experience experience);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Experience toEntity(ExperienceDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(ExperienceDto dto, @MappingTarget Experience entity);
}
