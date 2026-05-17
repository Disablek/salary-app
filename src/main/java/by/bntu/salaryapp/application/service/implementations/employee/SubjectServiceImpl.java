package by.bntu.salaryapp.application.service.implementations.employee;

import by.bntu.salaryapp.application.dto.employee.subject.SubjectDto;
import by.bntu.salaryapp.application.service.interfaces.employee.SubjectService;
import by.bntu.salaryapp.domain.model.employee.Subject;
import by.bntu.salaryapp.infrastructure.mapper.employee.subject.SubjectListMapper;
import by.bntu.salaryapp.infrastructure.mapper.employee.subject.SubjectMapper;
import by.bntu.salaryapp.infrastructure.persistence.repository.employee.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository repository;
    private final SubjectMapper mapper;
    private final SubjectListMapper listMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public SubjectDto create(SubjectDto dto) {
        String title = dto.getTitle().trim();

        if (repository.existsByTitle(title)) {
            throw new IllegalArgumentException("Subject with title '" + title + "' already exists");
        }

        Subject entity = mapper.toEntity(dto);
        entity.setTitle(title);

        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public SubjectDto update(UUID id, SubjectDto dto) {
        Subject entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with id: " + id));

        String newTitle = dto.getTitle().trim();

        if (!entity.getTitle().equals(newTitle) && repository.existsByTitle(newTitle)) {
            throw new IllegalArgumentException("Subject with title '" + newTitle + "' already exists");
        }

        mapper.updateFromDto(dto, entity);
        entity.setTitle(newTitle); // Явно устанавливаем, чтобы гарантировать trim()

        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Subject not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public SubjectDto getById(UUID id) {
        Subject entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with id: " + id));
        return mapper.toDto(entity);
    }

    @Override
    public List<SubjectDto> getAll() {
        return listMapper.toDtoList(repository.findAll());
    }
}
