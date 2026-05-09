package by.bntu.salaryapp.application.service.implementations.employee;

import by.bntu.salaryapp.application.dto.employee.employee.EmployeeDto;
import by.bntu.salaryapp.application.dto.employee.employee.EmployeeFilterDto;
import by.bntu.salaryapp.application.service.interfaces.employee.EmployeeService;
import by.bntu.salaryapp.domain.model.employee.Employee;
import by.bntu.salaryapp.infrastructure.mapper.employee.employee.EmployeeListMapper;
import by.bntu.salaryapp.infrastructure.mapper.employee.employee.EmployeeMapper;
import by.bntu.salaryapp.infrastructure.persistence.repository.employee.EmployeeRepository;
import by.bntu.salaryapp.infrastructure.persistence.specifications.employee.EmployeeSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;
    private final EmployeeListMapper listMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public EmployeeDto create(EmployeeDto dto) {
        Employee entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public EmployeeDto update(UUID id, EmployeeDto dto) {
        Employee entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        mapper.updateFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public EmployeeDto getById(UUID id) {
        Employee entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
        return mapper.toDto(entity);
    }

    @Override
    public List<EmployeeDto> getAll() {
        return listMapper.toDtoList(repository.findAll());
    }

    @Override
    public List<EmployeeDto> getByFilter(EmployeeFilterDto filter) {
//        Specification<Employee> spec = EmployeeSpecification.filter(filter);
//        return listMapper.toDtoList(repository.findAll(spec));
        return null;
    }
}
