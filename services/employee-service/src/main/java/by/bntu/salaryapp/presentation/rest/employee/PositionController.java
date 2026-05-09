package by.bntu.salaryapp.presentation.rest.employee;

import by.bntu.salaryapp.presentation.common.ApiEndpoints;
import by.bntu.salaryapp.application.dto.employee.position.PositionDto;
import by.bntu.salaryapp.application.dto.employee.position.PositionFilterDto;
import by.bntu.salaryapp.application.service.interfaces.employee.PositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping(ApiEndpoints.Position.BASE)
    public List<PositionDto> getPositions() {
        return positionService.getAll();
    }

    @PostMapping(ApiEndpoints.Position.BASE)
    public PositionDto createPosition(@Valid @RequestBody PositionDto dto) {
        return positionService.create(dto);
    }

    @PutMapping(ApiEndpoints.Position.BY_ID)
    public PositionDto updatePosition(@PathVariable UUID positionId, @Valid @RequestBody PositionDto dto) {
        return positionService.update(positionId, dto);
    }

    @DeleteMapping(ApiEndpoints.Position.BY_ID)
    public void deletePosition(@PathVariable UUID positionId) {
        positionService.delete(positionId);
    }

    @GetMapping(ApiEndpoints.Position.BY_ID)
    public PositionDto getPositionById(@PathVariable UUID positionId) {
        return positionService.getById(positionId);
    }
}
