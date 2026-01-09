package by.bntu.salaryapp.application.service.interfaces.employee;

import by.bntu.salaryapp.application.dto.employee.subject.SubjectDto;
import java.util.List;
import java.util.UUID;

public interface SubjectService {
    SubjectDto create(SubjectDto dto);

    SubjectDto update(UUID id, SubjectDto dto);

    void delete(UUID id);

    SubjectDto getById(UUID id);

    List<SubjectDto> getAll();
}