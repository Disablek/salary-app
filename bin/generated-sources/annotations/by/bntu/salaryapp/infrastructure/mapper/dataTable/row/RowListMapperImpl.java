package by.bntu.salaryapp.infrastructure.mapper.dataTable.row;

import by.bntu.salaryapp.application.dto.dataTable.row.RowDto;
import by.bntu.salaryapp.domain.model.dataTable.Row;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-10T13:02:19+0300",
    comments = "version: 1.6.0, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class RowListMapperImpl implements RowListMapper {

    @Autowired
    private RowMapper rowMapper;

    @Override
    public List<RowDto> toDto(List<Row> rows) {
        if ( rows == null ) {
            return null;
        }

        List<RowDto> list = new ArrayList<RowDto>( rows.size() );
        for ( Row row : rows ) {
            list.add( rowMapper.toDto( row ) );
        }

        return list;
    }

    @Override
    public List<Row> toEntity(List<RowDto> row) {
        if ( row == null ) {
            return null;
        }

        List<Row> list = new ArrayList<Row>( row.size() );
        for ( RowDto rowDto : row ) {
            list.add( rowMapper.toEntity( rowDto ) );
        }

        return list;
    }
}
