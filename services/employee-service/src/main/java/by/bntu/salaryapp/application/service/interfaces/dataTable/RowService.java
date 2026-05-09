package by.bntu.salaryapp.application.service.interfaces.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.row.RowDto;

import java.util.List;
import java.util.UUID;

public interface RowService {

    RowDto create(RowDto dto);

    RowDto update(RowDto dto);

    void delete(UUID id);

    RowDto getById(UUID id);

    /**
     * Получить все строки конкретной таблицы.
     */
    List<RowDto> findAllInTable(UUID tableId);

    /**
     * Получить все строки, привязанные к конкретному сотруднику.
     */
    List<RowDto> findAllByEmployee(UUID employeeId);
}