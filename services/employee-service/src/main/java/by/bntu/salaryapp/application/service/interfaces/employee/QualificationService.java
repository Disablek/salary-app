package by.bntu.salaryapp.application.service.interfaces.employee;

import by.bntu.salaryapp.application.dto.employee.qualification.QualificationDto;
import java.util.List;
import java.util.UUID;

public interface QualificationService {
    QualificationDto create(QualificationDto dto);

    QualificationDto update(UUID id, QualificationDto dto);

    void delete(UUID id);

    QualificationDto getById(UUID id);

    List<QualificationDto> getAll();
}