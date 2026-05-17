package by.bntu.salaryapp.application.service.implementations.employee;

import by.bntu.salaryapp.application.dto.employee.employee.EmployeeDto;
import by.bntu.salaryapp.application.dto.employee.employee.EmployeeFilterDto;
import by.bntu.salaryapp.application.service.interfaces.employee.EmployeeService;
import by.bntu.salaryapp.domain.model.employee.Employee;
import by.bntu.salaryapp.infrastructure.mapper.employee.employee.EmployeeListMapper;
import by.bntu.salaryapp.infrastructure.mapper.employee.employee.EmployeeMapper;
import by.bntu.salaryapp.infrastructure.persistence.repository.employee.ExperienceRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.employee.EmployeeRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.employee.PositionRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.employee.QualificationRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.employee.SubjectRepository;
import by.bntu.salaryapp.infrastructure.persistence.specifications.employee.EmployeeSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;
    private final EmployeeListMapper listMapper;
    private final PositionRepository positionRepository;
    private final SubjectRepository subjectRepository;
    private final QualificationRepository qualificationRepository;
    private final ExperienceRepository experienceRepository;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public EmployeeDto create(EmployeeDto dto) {
        Employee entity = new Employee();
        applyDto(entity, dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public EmployeeDto update(UUID id, EmployeeDto dto) {
        Employee entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        applyDto(entity, dto);
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

    private void applyDto(Employee entity, EmployeeDto dto) {
        String[] nameParts = splitFullName(dto.getFullName());
        entity.setLastName(nameParts[0]);
        entity.setFirstName(nameParts[1]);
        entity.setSurName(nameParts[2]);
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setHireDate(dto.getHireDate());
        entity.setYearsOfExperience(dto.getYearsOfExperience());

        entity.setPosition(dto.getPosition_id() == null
                ? null
                : positionRepository.getReferenceById(dto.getPosition_id()));
        entity.setQualification(dto.getQualification_id() == null
                ? null
                : qualificationRepository.getReferenceById(dto.getQualification_id()));
        entity.setExperience(dto.getExperience_id() == null
                ? null
                : experienceRepository.getReferenceById(dto.getExperience_id()));
        entity.setSubjects(dto.getSubjects_id() == null
                ? new HashSet<>()
                : new HashSet<>(subjectRepository.findAllById(dto.getSubjects_id())));
    }

    private String[] splitFullName(String fullName) {
        String[] parts = fullName == null ? new String[0] : fullName.trim().split("\\s+");
        String lastName = parts.length > 0 && !parts[0].isBlank() ? parts[0] : "Unknown";
        String firstName = parts.length > 1 && !parts[1].isBlank() ? parts[1] : lastName;
        String surName = parts.length > 2 ? String.join(" ", Arrays.copyOfRange(parts, 2, parts.length)) : null;
        return new String[]{lastName, firstName, surName};
    }
}
