package by.bntu.salaryapp.presentation.rest.dataTable.coefficient;

import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficientRule.CoefficientRuleDto;
import by.bntu.salaryapp.application.service.interfaces.dataTable.coefficient.CoefficientRuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//TODO: ЗАМЕНИТЬ
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coefficient-rules")
public class CoefficientRuleController {

    private final CoefficientRuleService ruleService;

    // Получить правила для конкретного коэффициента
    // GET /api/coefficient-rules?coefficientId=...
    @GetMapping
    public List<CoefficientRuleDto> getAllByCoefficient(@RequestParam UUID coefficientId) {
        return ruleService.findAllByCoefficientId(coefficientId);
    }

    @GetMapping("/{id}")
    public CoefficientRuleDto getById(@PathVariable UUID id) {
        return ruleService.findById(id);
    }

    @PostMapping
    public CoefficientRuleDto create(@Valid @RequestBody CoefficientRuleDto dto) {
        return ruleService.create(dto);
    }

    @PutMapping("/{id}")
    public CoefficientRuleDto update(@PathVariable UUID id, @Valid @RequestBody CoefficientRuleDto dto) {
        dto.setId(id);
        return ruleService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        ruleService.delete(id);
    }
}