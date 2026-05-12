package by.bntu.salaryapp.infrastructure.mapper.dataTable.cell;

import by.bntu.salaryapp.application.dto.dataTable.cell.CellDto;
import by.bntu.salaryapp.domain.model.dataTable.Cell;
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
public class CellListMapperImpl implements CellListMapper {

    @Autowired
    private CellMapper cellMapper;

    @Override
    public List<CellDto> toDtoList(List<Cell> cells) {
        if ( cells == null ) {
            return null;
        }

        List<CellDto> list = new ArrayList<CellDto>( cells.size() );
        for ( Cell cell : cells ) {
            list.add( cellMapper.toDto( cell ) );
        }

        return list;
    }

    @Override
    public List<Cell> toEntityList(List<CellDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Cell> list = new ArrayList<Cell>( dtos.size() );
        for ( CellDto cellDto : dtos ) {
            list.add( cellMapper.toEntity( cellDto ) );
        }

        return list;
    }
}
