package by.bntu.salaryapp.application.service.interfaces.employee;

import by.bntu.salaryapp.application.dto.employee.position.PositionDto;
import java.util.List;
import java.util.UUID;

public interface PositionService {
    PositionDto create(PositionDto dto);

    PositionDto update(UUID id, PositionDto dto);

    void delete(UUID id);

    PositionDto getById(UUID id);

    List<PositionDto> getAll();
}