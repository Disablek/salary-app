package by.bntu.salaryapp.datatable.presentation.rest;

import by.bntu.salaryapp.datatable.application.service.DataTableService;
import by.bntu.salaryapp.datatable.presentation.dto.CreateDataTableRequest;
import by.bntu.salaryapp.datatable.presentation.dto.DataTableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/datatables")
@RequiredArgsConstructor
public class DataTableController {

    private final DataTableService dataTableService;

    @PostMapping
    public ResponseEntity<DataTableDto> createDataTable(@RequestBody CreateDataTableRequest request) {
        DataTableDto dataTable = dataTableService.createDataTable(request);
        return ResponseEntity.ok(dataTable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataTableDto> getDataTable(@PathVariable UUID id) {
        DataTableDto dataTable = dataTableService.getDataTableById(id);
        return ResponseEntity.ok(dataTable);
    }

    @GetMapping
    public ResponseEntity<List<DataTableDto>> getAllDataTables() {
        List<DataTableDto> dataTables = dataTableService.getAllDataTables();
        return ResponseEntity.ok(dataTables);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataTableDto> updateDataTable(@PathVariable UUID id, @RequestBody CreateDataTableRequest request) {
        DataTableDto dataTable = dataTableService.updateDataTable(id, request);
        return ResponseEntity.ok(dataTable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDataTable(@PathVariable UUID id) {
        dataTableService.deleteDataTable(id);
        return ResponseEntity.noContent().build();
    }
}