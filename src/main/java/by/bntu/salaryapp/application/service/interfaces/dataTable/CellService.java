package by.bntu.salaryapp.application.service.interfaces.dataTable;


import by.bntu.salaryapp.application.dto.dataTable.cell.CellDto;

import java.util.List;
import java.util.UUID;

public interface CellService {
    CellDto findById(UUID id);

    CellDto create(CellDto cellDto);

    CellDto update(CellDto cellDto);

    void delete(UUID id);

    List<CellDto> findAllInDataTable(UUID dataTableId);

    List<CellDto> findAllInRow(UUID rowId);

    List<CellDto> findAllInColumn(UUID columnId);
}
