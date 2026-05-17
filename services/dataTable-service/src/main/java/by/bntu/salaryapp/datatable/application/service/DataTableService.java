package by.bntu.salaryapp.datatable.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import by.bntu.salaryapp.datatable.domain.model.DataTable;
import by.bntu.salaryapp.datatable.infrastructure.persistence.DataTableRepository;
import by.bntu.salaryapp.datatable.presentation.dto.DataTableCellDto;
import by.bntu.salaryapp.datatable.presentation.dto.DataTableColumnDto;
import by.bntu.salaryapp.datatable.presentation.dto.CreateDataTableRequest;
import by.bntu.salaryapp.datatable.presentation.dto.DataTableDto;
import by.bntu.salaryapp.datatable.presentation.dto.DataTableRowDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataTableService {

    private final DataTableRepository dataTableRepository;
    private final ObjectMapper objectMapper;

    private static final TypeReference<List<DataTableColumnDto>> COLUMNS_TYPE = new TypeReference<>() {};
    private static final TypeReference<List<DataTableRowDto>> ROWS_TYPE = new TypeReference<>() {};

    public DataTableDto createDataTable(CreateDataTableRequest request) {
        validateName(request.getName());

        List<DataTableColumnDto> columns = normalizeColumns(request.getColumns());
        List<DataTableRowDto> rows = normalizeRows(request.getRows());
        DataTable dataTable = DataTable.builder()
                .name(request.getName().trim())
                .description(blankToEmpty(request.getDescription()))
                .columnsJson(writeJson(columns))
                .rowsJson(writeJson(rows))
                .build();

        DataTable saved = dataTableRepository.save(dataTable);
        return mapToDto(saved);
    }

    public DataTableDto getDataTableById(UUID id) {
        DataTable dataTable = dataTableRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        return mapToDto(dataTable);
    }

    public List<DataTableDto> getAllDataTables() {
        return dataTableRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public DataTableDto updateDataTable(UUID id, CreateDataTableRequest request) {
        DataTable dataTable = dataTableRepository.findById(id)
                .orElseThrow(() -> notFound(id));

        if (request.getName() != null) {
            validateName(request.getName());
            dataTable.setName(request.getName().trim());
        }
        if (request.getDescription() != null) {
            dataTable.setDescription(blankToEmpty(request.getDescription()));
        }
        if (request.getColumns() != null) {
            dataTable.setColumnsJson(writeJson(normalizeColumns(request.getColumns())));
        }
        if (request.getRows() != null) {
            dataTable.setRowsJson(writeJson(normalizeRows(request.getRows())));
        }

        DataTable saved = dataTableRepository.save(dataTable);
        return mapToDto(saved);
    }

    public void deleteDataTable(UUID id) {
        if (!dataTableRepository.existsById(id)) {
            throw notFound(id);
        }
        dataTableRepository.deleteById(id);
    }

    private DataTableDto mapToDto(DataTable dataTable) {
        return new DataTableDto(
                dataTable.getId(),
                dataTable.getName(),
                dataTable.getDescription(),
                readJson(dataTable.getColumnsJson(), COLUMNS_TYPE),
                readJson(dataTable.getRowsJson(), ROWS_TYPE),
                dataTable.getCreatedAt(),
                dataTable.getUpdatedAt()
        );
    }

    private List<DataTableColumnDto> normalizeColumns(List<DataTableColumnDto> columns) {
        if (columns == null) {
            return new ArrayList<>();
        }

        List<DataTableColumnDto> normalized = new ArrayList<>();
        int order = 1;
        for (DataTableColumnDto column : columns) {
            if (column == null) {
                continue;
            }
            String name = blankToEmpty(column.getName()).trim();
            if (name.isEmpty()) {
                continue;
            }
            String type = blankToDefault(column.getType(), "STRING").trim().toUpperCase(Locale.ROOT);
            normalized.add(new DataTableColumnDto(
                    column.getId() != null ? column.getId() : UUID.randomUUID(),
                    name,
                    type,
                    order++,
                    blankToNull(column.getFormula())
            ));
        }
        return normalized;
    }

    private List<DataTableRowDto> normalizeRows(List<DataTableRowDto> rows) {
        if (rows == null) {
            return new ArrayList<>();
        }

        List<DataTableRowDto> normalized = new ArrayList<>();
        int order = 1;
        for (DataTableRowDto row : rows) {
            if (row == null) {
                continue;
            }
            List<DataTableCellDto> cells = new ArrayList<>();
            for (DataTableCellDto cell : safeCollection(row.getCells())) {
                if (cell == null || cell.getColumnId() == null) {
                    continue;
                }
                cells.add(new DataTableCellDto(
                        cell.getId() != null ? cell.getId() : UUID.randomUUID(),
                        cell.getColumnId(),
                        blankToEmpty(cell.getValue())
                ));
            }
            normalized.add(new DataTableRowDto(
                    row.getId() != null ? row.getId() : UUID.randomUUID(),
                    row.getEmployeeId(),
                    order++,
                    cells
            ));
        }
        return normalized;
    }

    private <T> List<T> readJson(String json, TypeReference<List<T>> typeReference) {
        if (json == null || json.isBlank()) {
            return new ArrayList<>();
        }
        try {
            List<T> value = objectMapper.readValue(json, typeReference);
            return value != null ? value : new ArrayList<>();
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Unable to deserialize table payload", exception);
        }
    }

    private String writeJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Unable to serialize table payload", exception);
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Table name is required");
        }
    }

    private ResponseStatusException notFound(UUID id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Data table not found: " + id);
    }

    private <T> Collection<T> safeCollection(Collection<T> values) {
        return values != null ? values : List.of();
    }

    private String blankToEmpty(String value) {
        return value == null ? "" : value;
    }

    private String blankToDefault(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }

    private String blankToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
