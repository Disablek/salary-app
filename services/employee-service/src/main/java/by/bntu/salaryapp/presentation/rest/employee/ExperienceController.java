package by.bntu.salaryapp.presentation.rest.employee;

import by.bntu.salaryapp.presentation.common.ApiEndpoints;
import by.bntu.salaryapp.application.dto.employee.experience.ExperienceDto;
import by.bntu.salaryapp.application.dto.employee.experience.ExperienceFilterDto;
import by.bntu.salaryapp.application.service.interfaces.employee.ExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping(ApiEndpoints.Experience.BASE)
    public List<ExperienceDto> getExperiences() {
        return experienceService.getAll();
    }

    @PostMapping(ApiEndpoints.Experience.BASE)
    public ExperienceDto createExperience(@Valid @RequestBody ExperienceDto dto) {
        return experienceService.create(dto);
    }

    @PutMapping(ApiEndpoints.Experience.BY_ID)
    public ExperienceDto updateExperience(@PathVariable UUID experienceId, @Valid @RequestBody ExperienceDto dto) {
        return experienceService.update(experienceId, dto);
    }

    @DeleteMapping(ApiEndpoints.Experience.BY_ID)
    public void deleteExperience(@PathVariable UUID experienceId) {
        experienceService.delete(experienceId);
    }

    @GetMapping(ApiEndpoints.Experience.BY_ID)
    public ExperienceDto getExperienceById(@PathVariable UUID experienceId) {
        return experienceService.getById(experienceId);
    }
}
