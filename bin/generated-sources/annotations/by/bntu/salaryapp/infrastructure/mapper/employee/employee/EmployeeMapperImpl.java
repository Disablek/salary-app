package by.bntu.salaryapp.infrastructure.mapper.employee.employee;

import by.bntu.salaryapp.application.dto.employee.employee.EmployeeDto;
import by.bntu.salaryapp.domain.model.employee.Employee;
import by.bntu.salaryapp.domain.model.employee.Experience;
import by.bntu.salaryapp.domain.model.employee.Position;
import by.bntu.salaryapp.domain.model.employee.Qualification;
import by.bntu.salaryapp.domain.model.user.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-10T13:02:19+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeDto toDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setPosition_id( employeePositionId( employee ) );
        employeeDto.setQualification_id( employeeQualificationId( employee ) );
        employeeDto.setExperience_id( employeeExperienceId( employee ) );
        employeeDto.setCreatedBy( employeeCreatedById( employee ) );
        employeeDto.setUpdatedBy( employeeUpdatedById( employee ) );
        employeeDto.setCreatedAt( employee.getCreatedDate() );
        employeeDto.setUpdatedAt( employee.getUpdatedAt() );

        employeeDto.setFullName( buildFullName(employee) );

        return employeeDto;
    }

    @Override
    public Employee toEntity(EmployeeDto dto) {
        if ( dto == null ) {
            return null;
        }

        Employee employee = new Employee();

        return employee;
    }

    @Override
    public void updateFromDto(EmployeeDto dto, Employee entity) {
        if ( dto == null ) {
            return;
        }
    }

    private UUID employeePositionId(Employee employee) {
        Position position = employee.getPosition();
        if ( position == null ) {
            return null;
        }
        return position.getId();
    }

    private UUID employeeQualificationId(Employee employee) {
        Qualification qualification = employee.getQualification();
        if ( qualification == null ) {
            return null;
        }
        return qualification.getId();
    }

    private UUID employeeExperienceId(Employee employee) {
        Experience experience = employee.getExperience();
        if ( experience == null ) {
            return null;
        }
        return experience.getId();
    }

    private UUID employeeCreatedById(Employee employee) {
        User createdBy = employee.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        return createdBy.getId();
    }

    private UUID employeeUpdatedById(Employee employee) {
        User updatedBy = employee.getUpdatedBy();
        if ( updatedBy == null ) {
            return null;
        }
        return updatedBy.getId();
    }
}
