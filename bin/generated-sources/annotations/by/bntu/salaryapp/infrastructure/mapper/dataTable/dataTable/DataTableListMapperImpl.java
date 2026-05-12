package by.bntu.salaryapp.infrastructure.mapper.dataTable.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.dataTable.DataTableDto;
import by.bntu.salaryapp.domain.model.dataTable.DataTable;
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
public class DataTableListMapperImpl implements DataTableListMapper {

    @Autowired
    private DataTableMapper dataTableMapper;

    @Override
    public List<DataTableDto> toDtoList(List<DataTable> tables) {
        if ( tables == null ) {
            return null;
        }

        List<DataTableDto> list = new ArrayList<DataTableDto>( tables.size() );
        for ( DataTable dataTable : tables ) {
            list.add( dataTableMapper.toDto( dataTable ) );
        }

        return list;
    }

    @Override
    public List<DataTable> toEntityList(List<DataTableDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<DataTable> list = new ArrayList<DataTable>( dtos.size() );
        for ( DataTableDto dataTableDto : dtos ) {
            list.add( dataTableMapper.toEntity( dataTableDto ) );
        }

        return list;
    }
}
