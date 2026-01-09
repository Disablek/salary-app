package by.bntu.salaryapp.presentation.rest.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.cell.CellDto;
import by.bntu.salaryapp.application.service.interfaces.dataTable.CellService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//TODO: ЗАМЕНИТЬ
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cells")
public class CellController {

    private final CellService cellService;

    @GetMapping
    public List<CellDto> getAllInTable(@RequestParam UUID tableId,
                                       @RequestParam(required = false) Integer page) {
        return cellService.findAllInDataTable(tableId, page);
    }

    @PostMapping
    public CellDto create(@Valid @RequestBody CellDto dto) {
        return cellService.create(dto);
    }

    @PutMapping("/{id}")
    public CellDto update(@PathVariable UUID id, @Valid @RequestBody CellDto dto) {
        dto.setId(id);
        return cellService.update(dto);
    }
}