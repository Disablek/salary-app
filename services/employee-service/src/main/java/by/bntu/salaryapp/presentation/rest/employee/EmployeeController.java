package by.bntu.salaryapp.presentation.rest.employee;

import by.bntu.salaryapp.presentation.common.ApiEndpoints;
import by.bntu.salaryapp.application.dto.employee.employee.EmployeeDto;
import by.bntu.salaryapp.application.dto.employee.employee.EmployeeFilterDto;
import by.bntu.salaryapp.application.service.interfaces.employee.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    @GetMapping(ApiEndpoints.Employee.BASE)
    public List<EmployeeDto> getEmployees() {
        return employeeService.getAll();
    }

    @PostMapping(ApiEndpoints.Employee.SEARCH)
    public List<EmployeeDto> searchEmployees(@RequestBody(required = false) EmployeeFilterDto filter) {
        return employeeService.getByFilter(filter);
    }

    @PostMapping(ApiEndpoints.Employee.BASE)
    public EmployeeDto createEmployee(@Valid @RequestBody EmployeeDto dto) {
        return employeeService.create(dto);
    }

    @PutMapping(ApiEndpoints.Employee.BY_ID)
    public EmployeeDto updateEmployee(@PathVariable UUID employeeId, @Valid @RequestBody EmployeeDto dto) {
        return employeeService.update(employeeId, dto);
    }

    @DeleteMapping(ApiEndpoints.Employee.BY_ID)
    public void deleteEmployee(@PathVariable UUID employeeId) {
        employeeService.delete(employeeId);
    }

    @GetMapping(ApiEndpoints.Employee.BY_ID)
    public EmployeeDto getEmployeeById(@PathVariable UUID employeeId) {
        return employeeService.getById(employeeId);
    }
}
