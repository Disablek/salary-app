package by.bntu.salaryapp.infrastructure.mapper.employee.position;

import by.bntu.salaryapp.application.dto.employee.position.PositionDto;
import by.bntu.salaryapp.domain.model.employee.Position;
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
public class PositionListMapperImpl implements PositionListMapper {

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public List<PositionDto> toDtoList(List<Position> positions) {
        if ( positions == null ) {
            return null;
        }

        List<PositionDto> list = new ArrayList<PositionDto>( positions.size() );
        for ( Position position : positions ) {
            list.add( positionMapper.toDto( position ) );
        }

        return list;
    }

    @Override
    public List<Position> toEntityList(List<PositionDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Position> list = new ArrayList<Position>( dtos.size() );
        for ( PositionDto positionDto : dtos ) {
            list.add( positionMapper.toEntity( positionDto ) );
        }

        return list;
    }
}
