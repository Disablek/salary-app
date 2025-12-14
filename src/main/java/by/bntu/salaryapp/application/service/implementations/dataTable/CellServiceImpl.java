package by.bntu.salaryapp.application.service.implementations.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.cell.CellDto;
import by.bntu.salaryapp.application.service.interfaces.dataTable.CellService;
import by.bntu.salaryapp.domain.model.dataTable.Cell;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.Row;
import by.bntu.salaryapp.infrastructure.mapper.dataTable.cell.CellListMapper;
import by.bntu.salaryapp.infrastructure.mapper.dataTable.cell.CellMapper;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.CellRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.ColumnRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.DataTableRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.RowRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CellServiceImpl implements CellService {

    private final CellRepository cellRepository;
    private final DataTableRepository dataTableRepository;
    private final RowRepository rowRepository;
    private final ColumnRepository columnRepository;

    private final CellMapper cellMapper;
    private final CellListMapper cellListMapper;

    @Override
    public CellDto findById(UUID id) {
        Cell cell = cellRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cell not found with id " + id));
        return cellMapper.toDto(cell);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER','ADMIN', 'USER')")
    public CellDto create(CellDto cellDto) {
        Row row = rowRepository.findById(cellDto.getRow_id())
                .orElseThrow(() -> new EntityNotFoundException("Row not found with id: " + cellDto.getRow_id()));

        Column column = columnRepository.findById(cellDto.getColumn_id())
                .orElseThrow(() -> new EntityNotFoundException("Column not found with id: " + cellDto.getColumn_id()));

        Cell cell = cellMapper.toEntity(cellDto);
        cell.setRow(row);
        cell.setColumn(column);

        if (cellDto.getValue() != null) {
            cell.setValue(cellDto.getValue());
        }

        Cell savedCell = cellRepository.save(cell);
        return cellMapper.toDto(savedCell);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER','ADMIN', 'USER')")
    public CellDto update(CellDto cellDto) {
        Cell existingCell = cellRepository.findById(cellDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cell not found with id " + cellDto.getId()));

        existingCell.setValue(cellDto.getValue());

        if (cellDto.getRow_id() != null && !existingCell.getRow().getId().equals(cellDto.getRow_id())) {
            Row newRow = rowRepository.findById(cellDto.getRow_id())
                    .orElseThrow(() -> new EntityNotFoundException("Row not found with id: " + cellDto.getRow_id()));
            existingCell.setRow(newRow);
        }

        if (cellDto.getColumn_id() != null && !existingCell.getColumn().getId().equals(cellDto.getColumn_id())) {
            Column newColumn = columnRepository.findById(cellDto.getColumn_id())
                    .orElseThrow(() -> new EntityNotFoundException("Column not found with id: " + cellDto.getColumn_id()));
            existingCell.setColumn(newColumn);
        }

        cellMapper.updateFromDto(cellDto, existingCell);

        Cell savedCell = cellRepository.save(existingCell);
        return cellMapper.toDto(savedCell);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER','ADMIN')")
    public void delete(UUID id) {
        if (!cellRepository.existsById(id)) {
            throw new EntityNotFoundException("Cell not found with id " + id);
        }
        cellRepository.deleteById(id);
    }

    @Override
    public List<CellDto> findAllInDataTable(UUID dataTableId) {
        if (!dataTableRepository.existsById(dataTableId)) {
            throw new EntityNotFoundException("DataTable not found with id " + dataTableId);
        }

        List<Cell> cells = cellRepository.findByRow_DataTable_Id(dataTableId);

        return cellListMapper.toDtoList(cells);
    }

    @Override
    public List<CellDto> findAllInRow(UUID rowId) {
        if (!rowRepository.existsById(rowId)) {
            throw new EntityNotFoundException("Row not found with id " + rowId);
        }

        List<Cell> cells = cellRepository.findByRowId(rowId);

        return cellListMapper.toDtoList(cells);
    }

    @Override
    public List<CellDto> findAllInColumn(UUID columnId) {
        if (!columnRepository.existsById(columnId)) {
            throw new EntityNotFoundException("Column not found with id " + columnId);
        }

        List<Cell> cells = cellRepository.findByColumnId(columnId);

        return cellListMapper.toDtoList(cells);
    }
}