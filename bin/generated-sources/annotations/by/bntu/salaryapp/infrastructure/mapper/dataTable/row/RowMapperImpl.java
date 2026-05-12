package by.bntu.salaryapp.infrastructure.mapper.dataTable.row;

import by.bntu.salaryapp.application.dto.dataTable.row.RowDto;
import by.bntu.salaryapp.domain.model.dataTable.DataTable;
import by.bntu.salaryapp.domain.model.dataTable.Row;
import by.bntu.salaryapp.domain.model.employee.Employee;
import by.bntu.salaryapp.domain.model.user.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-10T13:02:19+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class RowMapperImpl implements RowMapper {

    @Override
    public RowDto toDto(Row row) {
        if ( row == null ) {
            return null;
        }

        RowDto rowDto = new RowDto();

        rowDto.setTableId( rowTableId( row ) );
        rowDto.setEmployeeId( rowEmployeeId( row ) );
        rowDto.setCellsId( mapCellsToIds( row.getCells() ) );
        rowDto.setCreatedBy( rowCreatedById( row ) );
        rowDto.setUpdatedBy( rowUpdatedById( row ) );
        rowDto.setCreatedAt( row.getCreatedDate() );
        rowDto.setId( row.getId() );
        rowDto.setUpdatedAt( row.getUpdatedAt() );

        return rowDto;
    }

    @Override
    public Row toEntity(RowDto dto) {
        if ( dto == null ) {
            return null;
        }

        Row row = new Row();

        return row;
    }

    @Override
    public void updateFromDto(RowDto dto, Row entity) {
        if ( dto == null ) {
            return;
        }
    }

    private UUID rowTableId(Row row) {
        DataTable table = row.getTable();
        if ( table == null ) {
            return null;
        }
        return table.getId();
    }

    private UUID rowEmployeeId(Row row) {
        Employee employee = row.getEmployee();
        if ( employee == null ) {
            return null;
        }
        return employee.getId();
    }

    private UUID rowCreatedById(Row row) {
        User createdBy = row.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        return createdBy.getId();
    }

    private UUID rowUpdatedById(Row row) {
        User updatedBy = row.getUpdatedBy();
        if ( updatedBy == null ) {
            return null;
        }
        return updatedBy.getId();
    }
}
