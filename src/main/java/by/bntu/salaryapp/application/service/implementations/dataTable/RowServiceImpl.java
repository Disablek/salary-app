package by.bntu.salaryapp.application.service.implementations.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.row.RowDto;
import by.bntu.salaryapp.application.service.interfaces.dataTable.RowService;
import by.bntu.salaryapp.domain.model.dataTable.DataTable;
import by.bntu.salaryapp.domain.model.dataTable.Row;
import by.bntu.salaryapp.domain.model.employee.Employee;
import by.bntu.salaryapp.infrastructure.mapper.dataTable.row.RowListMapper;
import by.bntu.salaryapp.infrastructure.mapper.dataTable.row.RowMapper;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.DataTableRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.RowRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.employee.EmployeeRepository;
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
public class RowServiceImpl implements RowService {

    private final RowRepository rowRepository;
    private final DataTableRepository dataTableRepository;
    private final EmployeeRepository employeeRepository;

    private final RowMapper rowMapper;
    private final RowListMapper rowListMapper;



    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public RowDto create(RowDto dto) {
        DataTable table = dataTableRepository.findById(dto.getTableId())
                .orElseThrow(() -> new EntityNotFoundException("DataTable not found with id: " + dto.getTableId()));


        Employee employee = null;
        if (dto.getEmployeeId() != null) {
            employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + dto.getEmployeeId()));
        }

        Row row = rowMapper.toEntity(dto);
        row.setTable(table);
        row.setEmployee(employee);

        Row savedRow = rowRepository.save(row);
        return rowMapper.toDto(savedRow);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public RowDto update(RowDto dto) {
        Row existingRow = rowRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Row not found with id: " + dto.getId()));

        if (dto.getTableId() != null && !existingRow.getTable().getId().equals(dto.getTableId())) {
            DataTable newTable = dataTableRepository.findById(dto.getTableId())
                    .orElseThrow(() -> new EntityNotFoundException("DataTable not found with id: " + dto.getTableId()));
            existingRow.setTable(newTable);
        }

        if (dto.getEmployeeId() != null) {
            if (existingRow.getEmployee() == null || !existingRow.getEmployee().getId().equals(dto.getEmployeeId())) {
                Employee newEmployee = employeeRepository.findById(dto.getEmployeeId())
                        .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + dto.getEmployeeId()));
                existingRow.setEmployee(newEmployee);
            }
        } else {
            existingRow.setEmployee(null);
        }

        rowMapper.updateFromDto(dto, existingRow);

        Row savedRow = rowRepository.save(existingRow);
        return rowMapper.toDto(savedRow);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('SUPERUSER', 'ADMIN')")
    public void delete(UUID id) {
        if (!rowRepository.existsById(id)) {
            throw new EntityNotFoundException("Row not found with id: " + id);
        }
        rowRepository.deleteById(id);
    }

    @Override
    public RowDto getById(UUID id) {
        Row row = rowRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Row not found with id: " + id));
        return rowMapper.toDto(row);
    }

    @Override
    public List<RowDto> findAllInTable(UUID tableId) {
        if (!dataTableRepository.existsById(tableId)) {
            throw new EntityNotFoundException("DataTable not found with id: " + tableId);
        }
        List<Row> rows = rowRepository.findAllByTableId(tableId);
        return rowListMapper.toDto(rows);
    }

    @Override
    public List<RowDto> findAllByEmployee(UUID employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id: " + employeeId);
        }
        List<Row> rows = rowRepository.findAllByEmployeeId(employeeId);
        return rowListMapper.toDto(rows);
    }
}
