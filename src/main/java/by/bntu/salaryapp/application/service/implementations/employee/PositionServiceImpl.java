package by.bntu.salaryapp.application.service.implementations.employee;

import by.bntu.salaryapp.application.dto.employee.position.PositionDto;
import by.bntu.salaryapp.application.service.interfaces.employee.PositionService;
import by.bntu.salaryapp.domain.model.employee.Position;
import by.bntu.salaryapp.infrastructure.mapper.employee.position.PositionListMapper;
import by.bntu.salaryapp.infrastructure.mapper.employee.position.PositionMapper;
import by.bntu.salaryapp.infrastructure.persistence.repository.employee.PositionRepository;
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
public class PositionServiceImpl implements PositionService {

    private final PositionRepository repository;
    private final PositionMapper mapper;
    private final PositionListMapper listMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public PositionDto create(PositionDto dto) {
        String title = dto.getTitle().trim();

        if (repository.existsByTitle(title)) {
            throw new IllegalArgumentException("Position with title '" + title + "' already exists");
        }

        Position entity = mapper.toEntity(dto);
        entity.setTitle(title);

        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public PositionDto update(UUID id, PositionDto dto) {
        Position entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + id));

        String newTitle = dto.getTitle().trim();

        if (!entity.getTitle().equals(newTitle) && repository.existsByTitle(newTitle)) {
            throw new IllegalArgumentException("Position with title '" + newTitle + "' already exists");
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
            throw new EntityNotFoundException("Position not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public PositionDto getById(UUID id) {
        Position entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + id));
        return mapper.toDto(entity);
    }

    @Override
    public List<PositionDto> getAll() {
        return listMapper.toDtoList(repository.findAll());
    }
}