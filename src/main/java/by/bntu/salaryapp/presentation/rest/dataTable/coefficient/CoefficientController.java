package by.bntu.salaryapp.presentation.rest.dataTable.coefficient;

import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficient.CoefficientDto;
import by.bntu.salaryapp.application.service.interfaces.dataTable.coefficient.CoefficientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//TODO: ЗАМЕНИТЬ
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coefficients")
public class CoefficientController {

    private final CoefficientService coefficientService;

    @GetMapping
    public List<CoefficientDto> getAll() {
        // Здесь можно добавить фильтр "active" как параметр
        // Например, если фронт шлет ?active=true, вызываем findAllActive()
        return coefficientService.findAllActive();
    }

    @GetMapping("/{id}")
    public CoefficientDto getById(@PathVariable UUID id) {
        return coefficientService.findById(id);
    }

    @PostMapping
    public CoefficientDto create(@Valid @RequestBody CoefficientDto dto) {
        return coefficientService.create(dto);
    }

    @PutMapping("/{id}")
    public CoefficientDto update(@PathVariable UUID id, @Valid @RequestBody CoefficientDto dto) {
        dto.setId(id);
        return coefficientService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        coefficientService.delete(id);
    }
}