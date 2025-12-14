package by.bntu.salaryapp.application.service.interfaces.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.column.ColumnDto;

import java.util.List;
import java.util.UUID;

public interface ColumnService {

    ColumnDto create(ColumnDto dto);

    ColumnDto update(ColumnDto dto);

    void delete(UUID id);

    ColumnDto getById(UUID id);

    /**
     * Получить колонки таблицы.
     * @param tableId ID таблицы
     * @param pageNumber Номер страницы (1, 2). Если null - возвращает все колонки.
     */
    List<ColumnDto> getAllInTable(UUID tableId, Integer pageNumber);
}