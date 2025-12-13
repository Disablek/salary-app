package by.bntu.salaryapp.infrastructure.mapper.employee.employee;

import by.bntu.salaryapp.application.dto.employee.employee.EmployeeDto;
import by.bntu.salaryapp.domain.model.employee.Employee;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EmployeeMapper {

    @Mapping(
            expression = "java(buildFullName(employee))",
            target = "fullName"
    )
    @Mapping(source = "position.id", target = "position_id")
    @Mapping(source = "qualification.id", target = "qualification_id")
    @Mapping(source = "experience.id", target = "experience_id")
    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(source = "updatedBy.id", target = "updatedBy")
    @Mapping(source = "createdDate", target = "createdAt")
    EmployeeDto toDto(Employee employee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "surName", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "qualification", ignore = true)
    @Mapping(target = "experience", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Employee toEntity(EmployeeDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "qualification", ignore = true)
    @Mapping(target = "experience", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(EmployeeDto dto, @MappingTarget Employee entity);

    default String buildFullName(Employee employee) {
        if (employee == null) {
            return null;
        }

        return employee.getSurName() != null
                ? employee.getLastName() + " " + employee.getFirstName() + " " + employee.getSurName()
                : employee.getLastName() + " " + employee.getFirstName();
    }
}

