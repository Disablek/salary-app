package by.bntu.salaryapp.application.service.interfaces.employee;

import by.bntu.salaryapp.application.dto.employee.experience.ExperienceDto;
import java.util.List;
import java.util.UUID;

public interface ExperienceService {
    ExperienceDto create(ExperienceDto dto);

    ExperienceDto update(UUID id, ExperienceDto dto);

    void delete(UUID id);

    ExperienceDto getById(UUID id);

    List<ExperienceDto> getAll();
}