package by.bntu.salaryapp.application.service.interfaces.employee;

import by.bntu.salaryapp.application.dto.employee.employee.EmployeeDto;
import by.bntu.salaryapp.application.dto.employee.employee.EmployeeFilterDto;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    EmployeeDto create(EmployeeDto dto);

    EmployeeDto update(UUID id, EmployeeDto dto);

    void delete(UUID id);

    EmployeeDto getById(UUID id);

    List<EmployeeDto> getAll();

    List<EmployeeDto> getByFilter(EmployeeFilterDto filter);
}
