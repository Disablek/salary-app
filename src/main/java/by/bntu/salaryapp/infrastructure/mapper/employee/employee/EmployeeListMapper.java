package by.bntu.salaryapp.infrastructure.mapper.employee.employee;

import by.bntu.salaryapp.application.dto.employee.employee.EmployeeDto;
import by.bntu.salaryapp.domain.model.employee.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface EmployeeListMapper {

    List<EmployeeDto> toDtoList(List<Employee> employees);

    List<Employee> toEntityList(List<EmployeeDto> dtos);
}
