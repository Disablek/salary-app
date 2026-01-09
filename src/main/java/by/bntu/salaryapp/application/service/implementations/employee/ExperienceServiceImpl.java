package by.bntu.salaryapp.application.service.implementations.employee;

import by.bntu.salaryapp.application.dto.employee.experience.ExperienceDto;
import by.bntu.salaryapp.application.service.interfaces.employee.ExperienceService;
import by.bntu.salaryapp.domain.model.employee.Experience;
import by.bntu.salaryapp.infrastructure.mapper.employee.experience.ExperienceListMapper;
import by.bntu.salaryapp.infrastructure.mapper.employee.experience.ExperienceMapper;
import by.bntu.salaryapp.infrastructure.persistence.repository.employee.ExperienceRepository;
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
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository repository;
    private final ExperienceMapper mapper;
    private final ExperienceListMapper listMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public ExperienceDto create(ExperienceDto dto) {
        String title = dto.getTitle().trim();

        if (repository.existsByTitle(title)) {
            throw new IllegalArgumentException("Experience with title '" + title + "' already exists");
        }

        Experience entity = mapper.toEntity(dto);
        entity.setTitle(title);

        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public ExperienceDto update(UUID id, ExperienceDto dto) {
        Experience entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Experience not found with id: " + id));

        String newTitle = dto.getTitle().trim();

        if (!entity.getTitle().equals(newTitle) && repository.existsByTitle(newTitle)) {
            throw new IllegalArgumentException("Experience with title '" + newTitle + "' already exists");
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
            throw new EntityNotFoundException("Experience not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public ExperienceDto getById(UUID id) {
        Experience entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Experience not found with id: " + id));
        return mapper.toDto(entity);
    }

    @Override
    public List<ExperienceDto> getAll() {
        return listMapper.toDtoList(repository.findAll());
    }
}