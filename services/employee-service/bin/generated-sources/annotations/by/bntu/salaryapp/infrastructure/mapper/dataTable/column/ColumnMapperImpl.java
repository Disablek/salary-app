package by.bntu.salaryapp.infrastructure.mapper.dataTable.column;

import by.bntu.salaryapp.application.dto.dataTable.column.ColumnDto;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.DataTable;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.user.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-11T07:55:03+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class ColumnMapperImpl implements ColumnMapper {

    @Override
    public ColumnDto toDto(Column column) {
        if ( column == null ) {
            return null;
        }

        ColumnDto columnDto = new ColumnDto();

        columnDto.setDataTable_id( columnMainTableId( column ) );
        columnDto.setCoefficient_id( columnCoefficientId( column ) );
        columnDto.setCreatedBy( columnCreatedById( column ) );
        columnDto.setUpdatedBy( columnUpdatedById( column ) );
        columnDto.setCreatedAt( column.getCreatedDate() );
        columnDto.setCellsId( mapCellsToIds( column.getCells() ) );
        columnDto.setId( column.getId() );
        columnDto.setKey( column.getKey() );
        columnDto.setTitle( column.getTitle() );
        columnDto.setActiveInPage( column.getActiveInPage() );
        columnDto.setDataType( column.getDataType() );
        columnDto.setUpdatedAt( column.getUpdatedAt() );

        return columnDto;
    }

    @Override
    public Column toEntity(ColumnDto dto) {
        if ( dto == null ) {
            return null;
        }

        Column column = new Column();

        column.setActiveInPage( dto.getActiveInPage() );
        column.setDataType( dto.getDataType() );
        column.setKey( dto.getKey() );
        column.setTitle( dto.getTitle() );

        return column;
    }

    @Override
    public void updateFromDto(ColumnDto dto, Column entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getActiveInPage() != null ) {
            entity.setActiveInPage( dto.getActiveInPage() );
        }
        if ( dto.getDataType() != null ) {
            entity.setDataType( dto.getDataType() );
        }
        if ( dto.getKey() != null ) {
            entity.setKey( dto.getKey() );
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
    }

    private UUID columnMainTableId(Column column) {
        DataTable mainTable = column.getMainTable();
        if ( mainTable == null ) {
            return null;
        }
        return mainTable.getId();
    }

    private UUID columnCoefficientId(Column column) {
        Coefficient coefficient = column.getCoefficient();
        if ( coefficient == null ) {
            return null;
        }
        return coefficient.getId();
    }

    private UUID columnCreatedById(Column column) {
        User createdBy = column.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        return createdBy.getId();
    }

    private UUID columnUpdatedById(Column column) {
        User updatedBy = column.getUpdatedBy();
        if ( updatedBy == null ) {
            return null;
        }
        return updatedBy.getId();
    }
}
