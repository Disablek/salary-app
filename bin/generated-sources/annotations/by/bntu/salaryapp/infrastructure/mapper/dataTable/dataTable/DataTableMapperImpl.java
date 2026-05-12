package by.bntu.salaryapp.infrastructure.mapper.dataTable.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.dataTable.DataTableDto;
import by.bntu.salaryapp.domain.model.dataTable.DataTable;
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
public class DataTableMapperImpl implements DataTableMapper {

    @Override
    public DataTableDto toDto(DataTable table) {
        if ( table == null ) {
            return null;
        }

        DataTableDto dataTableDto = new DataTableDto();

        dataTableDto.setColumns_id( mapColumnsToIds( table.getColumns() ) );
        dataTableDto.setRows_id( mapRowsToIds( table.getRows() ) );
        dataTableDto.setCreatedBy( tableCreatedById( table ) );
        dataTableDto.setUpdatedBy( tableUpdatedById( table ) );
        dataTableDto.setCreatedAt( table.getCreatedDate() );
        dataTableDto.setDescription( table.getDescription() );
        dataTableDto.setId( table.getId() );
        dataTableDto.setName( table.getName() );
        dataTableDto.setUpdatedAt( table.getUpdatedAt() );

        return dataTableDto;
    }

    @Override
    public DataTable toEntity(DataTableDto dto) {
        if ( dto == null ) {
            return null;
        }

        DataTable dataTable = new DataTable();

        dataTable.setDescription( dto.getDescription() );
        dataTable.setName( dto.getName() );

        return dataTable;
    }

    @Override
    public void updateFromDto(DataTableDto dto, DataTable entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
    }

    private UUID tableCreatedById(DataTable dataTable) {
        User createdBy = dataTable.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        return createdBy.getId();
    }

    private UUID tableUpdatedById(DataTable dataTable) {
        User updatedBy = dataTable.getUpdatedBy();
        if ( updatedBy == null ) {
            return null;
        }
        return updatedBy.getId();
    }
}
