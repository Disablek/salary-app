package by.bntu.salaryapp.datatable.application.service;

import by.bntu.salaryapp.datatable.domain.model.DataTable;
import by.bntu.salaryapp.datatable.infrastructure.persistence.DataTableRepository;
import by.bntu.salaryapp.datatable.presentation.dto.CreateDataTableRequest;
import by.bntu.salaryapp.datatable.presentation.dto.DataTableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataTableService {

    private final DataTableRepository dataTableRepository;

    public DataTableDto createDataTable(CreateDataTableRequest request) {
        DataTable dataTable = DataTable.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        DataTable saved = dataTableRepository.save(dataTable);
        return mapToDto(saved);
    }

    public DataTableDto getDataTableById(UUID id) {
        DataTable dataTable = dataTableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DataTable not found"));
        return mapToDto(dataTable);
    }

    public List<DataTableDto> getAllDataTables() {
        return dataTableRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public DataTableDto updateDataTable(UUID id, CreateDataTableRequest request) {
        DataTable dataTable = dataTableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DataTable not found"));

        dataTable.setName(request.getName());
        dataTable.setDescription(request.getDescription());

        DataTable saved = dataTableRepository.save(dataTable);
        return mapToDto(saved);
    }

    public void deleteDataTable(UUID id) {
        if (!dataTableRepository.existsById(id)) {
            throw new RuntimeException("DataTable not found");
        }
        dataTableRepository.deleteById(id);
    }

    private DataTableDto mapToDto(DataTable dataTable) {
        return new DataTableDto(
                dataTable.getId(),
                dataTable.getName(),
                dataTable.getDescription(),
                dataTable.getCreatedAt(),
                dataTable.getUpdatedAt()
        );
    }
}