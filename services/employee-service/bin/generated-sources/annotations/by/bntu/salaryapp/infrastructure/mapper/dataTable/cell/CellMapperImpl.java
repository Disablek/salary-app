package by.bntu.salaryapp.infrastructure.mapper.dataTable.cell;

import by.bntu.salaryapp.application.dto.dataTable.cell.CellDto;
import by.bntu.salaryapp.domain.model.dataTable.Cell;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.Row;
import by.bntu.salaryapp.domain.model.user.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-10T13:01:34+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class CellMapperImpl implements CellMapper {

    @Override
    public CellDto toDto(Cell cell) {
        if ( cell == null ) {
            return null;
        }

        CellDto cellDto = new CellDto();

        cellDto.setRow_id( cellRowId( cell ) );
        cellDto.setColumn_id( cellColumnId( cell ) );
        cellDto.setCreatedBy( cellCreatedById( cell ) );
        cellDto.setUpdatedBy( cellUpdatedById( cell ) );
        cellDto.setCreatedAt( cell.getCreatedDate() );
        cellDto.setId( cell.getId() );
        cellDto.setUpdatedAt( cell.getUpdatedAt() );
        cellDto.setValue( cell.getValue() );

        return cellDto;
    }

    @Override
    public Cell toEntity(CellDto dto) {
        if ( dto == null ) {
            return null;
        }

        Cell cell = new Cell();

        cell.setValue( dto.getValue() );

        return cell;
    }

    @Override
    public void updateFromDto(CellDto dto, Cell entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getValue() != null ) {
            entity.setValue( dto.getValue() );
        }
    }

    private UUID cellRowId(Cell cell) {
        Row row = cell.getRow();
        if ( row == null ) {
            return null;
        }
        return row.getId();
    }

    private UUID cellColumnId(Cell cell) {
        Column column = cell.getColumn();
        if ( column == null ) {
            return null;
        }
        return column.getId();
    }

    private UUID cellCreatedById(Cell cell) {
        User createdBy = cell.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        return createdBy.getId();
    }

    private UUID cellUpdatedById(Cell cell) {
        User updatedBy = cell.getUpdatedBy();
        if ( updatedBy == null ) {
            return null;
        }
        return updatedBy.getId();
    }
}
