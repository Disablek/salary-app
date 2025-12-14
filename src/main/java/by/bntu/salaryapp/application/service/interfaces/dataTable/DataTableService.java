package by.bntu.salaryapp.application.service.interfaces.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.dataTable.DataTableDto;

import java.util.List;
import java.util.UUID;

public interface DataTableService {
    DataTableDto create(DataTableDto dto);

    DataTableDto update(DataTableDto dto);

    void delete(UUID id);

    DataTableDto getById(UUID id);

    List<DataTableDto> getAll();
}