package by.bntu.salaryapp.application.service.implementations.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.column.ColumnDto;
import by.bntu.salaryapp.application.service.interfaces.dataTable.ColumnService;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.DataTable;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import by.bntu.salaryapp.infrastructure.mapper.dataTable.column.ColumnListMapper;
import by.bntu.salaryapp.infrastructure.mapper.dataTable.column.ColumnMapper;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.coefficient.CoefficientRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.ColumnRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.DataTableRepository;
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
public class ColumnServiceImpl implements ColumnService {

    private final ColumnRepository columnRepository;
    private final DataTableRepository dataTableRepository;
    private final CoefficientRepository coefficientRepository;

    private final ColumnMapper columnMapper;
    private final ColumnListMapper columnListMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public ColumnDto create(ColumnDto dto) {
        DataTable dataTable = dataTableRepository.findById(dto.getDataTable_id())
                .orElseThrow(() -> new EntityNotFoundException("DataTable not found with id: " + dto.getDataTable_id()));

        Column column = columnMapper.toEntity(dto);
        column.setMainTable(dataTable);

        if (dto.getCoefficient_id() != null) {
            Coefficient coefficient = coefficientRepository.findById(dto.getCoefficient_id())
                    .orElseThrow(() -> new EntityNotFoundException("Coefficient not found with id: " + dto.getCoefficient_id()));
            column.setCoefficient(coefficient);
        }

        Column savedColumn = columnRepository.save(column);
        return columnMapper.toDto(savedColumn);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public ColumnDto update(ColumnDto dto) {
        Column existingColumn = columnRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Column not found with id: " + dto.getId()));
        columnMapper.updateFromDto(dto, existingColumn);

        if (dto.getDataTable_id() != null && !existingColumn.getMainTable().getId().equals(dto.getDataTable_id())) {
            DataTable newDataTable = dataTableRepository.findById(dto.getDataTable_id())
                    .orElseThrow(() -> new EntityNotFoundException("DataTable not found with id: " + dto.getDataTable_id()));
            existingColumn.setMainTable(newDataTable);
        }

        if (dto.getCoefficient_id() != null) {
            if (existingColumn.getCoefficient() == null || !existingColumn.getCoefficient().getId().equals(dto.getCoefficient_id())) {
                Coefficient newCoefficient = coefficientRepository.findById(dto.getCoefficient_id())
                        .orElseThrow(() -> new EntityNotFoundException("Coefficient not found with id: " + dto.getCoefficient_id()));
                existingColumn.setCoefficient(newCoefficient);
            }
        } else {
            existingColumn.setCoefficient(null);
        }

        Column savedColumn = columnRepository.save(existingColumn);
        return columnMapper.toDto(savedColumn);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public void delete(UUID id) {
        if (!columnRepository.existsById(id)) {
            throw new EntityNotFoundException("Column not found with id: " + id);
        }
        columnRepository.deleteById(id);
    }

    @Override
    public ColumnDto getById(UUID id) {
        Column column = columnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Column not found with id: " + id));
        return columnMapper.toDto(column);
    }

    @Override
    public List<ColumnDto> getAllInTable(UUID tableId, Integer pageNumber) {
        if (!dataTableRepository.existsById(tableId)) {
            throw new EntityNotFoundException("DataTable not found with id: " + tableId);
        }

        List<Column> columns;

        if (pageNumber == null) {
            columns = columnRepository.findAllByMainTableId(tableId);
        } else {
            List<Short> allowedPages;
            if (pageNumber == 1) {
                allowedPages = List.of((short) 0, (short) 1);
            } else if (pageNumber == 2) {
                allowedPages = List.of((short) 0, (short) 2);
            } else {
                throw new IllegalArgumentException("Invalid dataTable number");
            }

            columns = columnRepository.findAllByMainTableIdAndActiveInPageIn(tableId, allowedPages);
        }

        return columnListMapper.toDtoList(columns);
    }
}
