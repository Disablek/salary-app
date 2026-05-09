package by.bntu.salaryapp.presentation.rest.employee;

import by.bntu.salaryapp.presentation.common.ApiEndpoints;
import by.bntu.salaryapp.application.dto.employee.qualification.QualificationDto;
import by.bntu.salaryapp.application.dto.employee.qualification.QualificationFilterDto;
import by.bntu.salaryapp.application.service.interfaces.employee.QualificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class QualificationController {

    private final QualificationService qualificationService;

    @GetMapping(ApiEndpoints.Qualification.BASE)
    public List<QualificationDto> getQualifications() {
        return qualificationService.getAll();
    }

    @PostMapping(ApiEndpoints.Qualification.BASE)
    public QualificationDto createQualification(@Valid @RequestBody QualificationDto dto) {
        return qualificationService.create(dto);
    }

    @PutMapping(ApiEndpoints.Qualification.BY_ID)
    public QualificationDto updateQualification(@PathVariable UUID qualificationId, @Valid @RequestBody QualificationDto dto) {
        return qualificationService.update(qualificationId, dto);
    }

    @DeleteMapping(ApiEndpoints.Qualification.BY_ID)
    public void deleteQualification(@PathVariable UUID qualificationId) {
        qualificationService.delete(qualificationId);
    }

    @GetMapping(ApiEndpoints.Qualification.BY_ID)
    public QualificationDto getQualificationById(@PathVariable UUID qualificationId) {
        return qualificationService.getById(qualificationId);
    }
}
