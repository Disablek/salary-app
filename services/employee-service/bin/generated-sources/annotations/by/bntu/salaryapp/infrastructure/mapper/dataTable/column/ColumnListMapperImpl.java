package by.bntu.salaryapp.infrastructure.mapper.dataTable.column;

import by.bntu.salaryapp.application.dto.dataTable.column.ColumnDto;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-10T22:08:18+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class ColumnListMapperImpl implements ColumnListMapper {

    @Autowired
    private ColumnMapper columnMapper;

    @Override
    public List<ColumnDto> toDtoList(List<Column> columns) {
        if ( columns == null ) {
            return null;
        }

        List<ColumnDto> list = new ArrayList<ColumnDto>( columns.size() );
        for ( Column column : columns ) {
            list.add( columnMapper.toDto( column ) );
        }

        return list;
    }

    @Override
    public List<Column> toEntityList(List<ColumnDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Column> list = new ArrayList<Column>( dtos.size() );
        for ( ColumnDto columnDto : dtos ) {
            list.add( columnMapper.toEntity( columnDto ) );
        }

        return list;
    }
}
