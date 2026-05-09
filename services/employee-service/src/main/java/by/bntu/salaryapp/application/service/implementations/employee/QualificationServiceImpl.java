package by.bntu.salaryapp.application.service.implementations.employee;

import by.bntu.salaryapp.application.dto.employee.qualification.QualificationDto;
import by.bntu.salaryapp.application.service.interfaces.employee.QualificationService;
import by.bntu.salaryapp.domain.model.employee.Qualification;
import by.bntu.salaryapp.infrastructure.mapper.employee.qualification.QualificationListMapper;
import by.bntu.salaryapp.infrastructure.mapper.employee.qualification.QualificationMapper;
import by.bntu.salaryapp.infrastructure.persistence.repository.employee.QualificationRepository;
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
public class QualificationServiceImpl implements QualificationService {

    private final QualificationRepository repository;
    private final QualificationMapper mapper;
    private final QualificationListMapper listMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public QualificationDto create(QualificationDto dto) {
        String title = dto.getTitle().trim();

        if (repository.existsByTitle(title)) {
            throw new IllegalArgumentException("Qualification with title '" + title + "' already exists");
        }

        Qualification entity = mapper.toEntity(dto);
        entity.setTitle(title);

        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public QualificationDto update(UUID id, QualificationDto dto) {
        Qualification entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Qualification not found with id: " + id));

        String newTitle = dto.getTitle().trim();

        if (!entity.getTitle().equals(newTitle) && repository.existsByTitle(newTitle)) {
            throw new IllegalArgumentException("Qualification with title '" + newTitle + "' already exists");
        }

        mapper.updateFromDto(dto, entity);
        entity.setTitle(newTitle);

        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Qualification not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public QualificationDto getById(UUID id) {
        Qualification entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Qualification not found with id: " + id));
        return mapper.toDto(entity);
    }

    @Override
    public List<QualificationDto> getAll() {
        return listMapper.toDtoList(repository.findAll());
    }
}