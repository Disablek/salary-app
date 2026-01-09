package by.bntu.salaryapp.presentation.rest.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.dataTable.DataTableDto;
import by.bntu.salaryapp.application.service.interfaces.dataTable.DataTableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//TODO: ЗАМЕНИТЬ
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/datatables") // Можно вынести в ApiEndpoints
public class DataTableController {

    private final DataTableService dataTableService;

    @GetMapping
    public List<DataTableDto> getAll() {
        return dataTableService.getAll();
    }

    @GetMapping("/{id}")
    public DataTableDto getById(@PathVariable UUID id) {
        return dataTableService.getById(id);
    }

    @PostMapping
    public DataTableDto create(@Valid @RequestBody DataTableDto dto) {
        return dataTableService.create(dto);
    }

    @PutMapping("/{id}")
    public DataTableDto update(@PathVariable UUID id, @Valid @RequestBody DataTableDto dto) {
        dto.setId(id);
        return dataTableService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        dataTableService.delete(id);
    }
}