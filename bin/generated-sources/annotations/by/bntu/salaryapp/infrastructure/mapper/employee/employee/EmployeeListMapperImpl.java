package by.bntu.salaryapp.infrastructure.mapper.employee.employee;

import by.bntu.salaryapp.application.dto.employee.employee.EmployeeDto;
import by.bntu.salaryapp.domain.model.employee.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-10T13:02:19+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class EmployeeListMapperImpl implements EmployeeListMapper {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDto> toDtoList(List<Employee> employees) {
        if ( employees == null ) {
            return null;
        }

        List<EmployeeDto> list = new ArrayList<EmployeeDto>( employees.size() );
        for ( Employee employee : employees ) {
            list.add( employeeMapper.toDto( employee ) );
        }

        return list;
    }

    @Override
    public List<Employee> toEntityList(List<EmployeeDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Employee> list = new ArrayList<Employee>( dtos.size() );
        for ( EmployeeDto employeeDto : dtos ) {
            list.add( employeeMapper.toEntity( employeeDto ) );
        }

        return list;
    }
}
