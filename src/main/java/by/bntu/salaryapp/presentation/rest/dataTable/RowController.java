package by.bntu.salaryapp.presentation.rest.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.row.RowDto;
import by.bntu.salaryapp.application.service.interfaces.dataTable.RowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//TODO: ЗАМЕНИТЬ
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rows")
public class RowController {

    private final RowService rowService;

    @GetMapping
    public List<RowDto> getAllInTable(@RequestParam UUID tableId) {
        return rowService.findAllInTable(tableId);
    }

    @PostMapping
    public RowDto create(@Valid @RequestBody RowDto dto) {
        return rowService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        rowService.delete(id);
    }
}