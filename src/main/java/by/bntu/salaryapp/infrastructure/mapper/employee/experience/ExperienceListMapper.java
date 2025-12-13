package by.bntu.salaryapp.infrastructure.mapper.employee.experience;

import by.bntu.salaryapp.application.dto.employee.experience.ExperienceDTO;
import by.bntu.salaryapp.domain.model.employee.Experience;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ExperienceMapper.class)
public interface ExperienceListMapper {

    List<ExperienceDTO> toDtoList(List<Experience> experiences);

    List<Experience> toEntityList(List<ExperienceDTO> dtos);
}
