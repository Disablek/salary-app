package by.bntu.salaryapp.infrastructure.mapper.employee.experience;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.employee.experience.ExperienceDto;
import by.bntu.salaryapp.domain.model.employee.Experience;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = ExperienceMapper.class)
public interface ExperienceListMapper {

    List<ExperienceDto> toDtoList(List<Experience> experiences);

    List<Experience> toEntityList(List<ExperienceDto> dtos);
}
