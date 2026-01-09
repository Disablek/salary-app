package by.bntu.salaryapp.presentation.rest.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.column.ColumnDto;
import by.bntu.salaryapp.application.service.interfaces.dataTable.ColumnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//TODO: ЗАМЕНИТЬ
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/columns")
public class ColumnController {

    private final ColumnService columnService;

    // Получить колонки для конкретной таблицы и страницы
    // GET /api/columns?tableId=...&page=1
    @GetMapping
    public List<ColumnDto> getAllInTable(@RequestParam UUID tableId,
                                         @RequestParam(required = false) Integer page) {
        return columnService.getAllInTable(tableId, page);
    }

    @GetMapping("/{id}")
    public ColumnDto getById(@PathVariable UUID id) {
        return columnService.getById(id);
    }

    @PostMapping
    public ColumnDto create(@Valid @RequestBody ColumnDto dto) {
        return columnService.create(dto);
    }

    @PutMapping("/{id}")
    public ColumnDto update(@PathVariable UUID id, @Valid @RequestBody ColumnDto dto) {
        dto.setId(id);
        return columnService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        columnService.delete(id);
    }
}