package by.bntu.salaryapp.presentation.rest.employee;

import by.bntu.salaryapp.presentation.common.ApiEndpoints;
import by.bntu.salaryapp.application.dto.employee.subject.SubjectDto;
import by.bntu.salaryapp.application.dto.employee.subject.SubjectFilterDto;
import by.bntu.salaryapp.application.service.interfaces.employee.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping(ApiEndpoints.Subject.BASE)
    public List<SubjectDto> getSubjects() {
        return subjectService.getAll();
    }

    @PostMapping(ApiEndpoints.Subject.BASE)
    public SubjectDto createSubject(@Valid @RequestBody SubjectDto dto) {
        return subjectService.create(dto);
    }

    @PutMapping(ApiEndpoints.Subject.BY_ID)
    public SubjectDto updateSubject(@PathVariable UUID subjectId, @Valid @RequestBody SubjectDto dto) {
        return subjectService.update(subjectId, dto);
    }

    @DeleteMapping(ApiEndpoints.Subject.BY_ID)
    public void deleteSubject(@PathVariable UUID subjectId) {
        subjectService.delete(subjectId);
    }

    @GetMapping(ApiEndpoints.Subject.BY_ID)
    public SubjectDto getSubjectById(@PathVariable UUID subjectId) {
        return subjectService.getById(subjectId);
    }
}
