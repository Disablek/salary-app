package by.bntu.salaryapp.application.service.implementations.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.dataTable.DataTableDto;
import by.bntu.salaryapp.application.service.interfaces.dataTable.DataTableService;
import by.bntu.salaryapp.domain.common.enums.ColumnDataType;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.DataTable;
import by.bntu.salaryapp.infrastructure.mapper.dataTable.dataTable.DataTableListMapper;
import by.bntu.salaryapp.infrastructure.mapper.dataTable.dataTable.DataTableMapper;
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
public class DataTableServiceImpl implements DataTableService {

    private final DataTableRepository dataTableRepository;
    private final DataTableMapper dataTableMapper;
    private final DataTableListMapper dataTableListMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public DataTableDto create(DataTableDto dto) {
        DataTable dataTable = dataTableMapper.toEntity(dto);
        if (dataTable.getColumns() != null) dataTable.getColumns().clear();
        if (dataTable.getRows() != null) dataTable.getRows().clear();

        DataTable savedTable = dataTableRepository.save(dataTable);

        Column idColumn = Column.builder()
                .mainTable(savedTable)
                .title("№")
                .key("sys_row_number")
                .activeInPage((short) 0)
                .dataType(ColumnDataType.Integer)
                .build();


        savedTable.getColumns().add(idColumn);
        dataTableRepository.save(savedTable);

        //TODO:
        // Для надежности лучше использовать ColumnRepository напрямую здесь
        return dataTableMapper.toDto(savedTable);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public DataTableDto update(DataTableDto dto) {
        DataTable existingTable = dataTableRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("DataTable not found with id: " + dto.getId()));

        dataTableMapper.updateFromDto(dto, existingTable);

        DataTable savedTable = dataTableRepository.save(existingTable);
        return dataTableMapper.toDto(savedTable);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public void delete(UUID id) {
        if (!dataTableRepository.existsById(id)) {
            throw new EntityNotFoundException("DataTable not found with id: " + id);
        }

        dataTableRepository.deleteById(id);
    }

    @Override
    public DataTableDto getById(UUID id) {
        DataTable dataTable = dataTableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DataTable not found with id: " + id));
        return dataTableMapper.toDto(dataTable);
    }

    @Override
    public List<DataTableDto> getAll() {
        List<DataTable> tables = dataTableRepository.findAll();
        return dataTableListMapper.toDtoList(tables);
    }
}