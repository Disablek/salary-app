package by.bntu.salaryapp.infrastructure.mapper.employee.position;

import by.bntu.salaryapp.application.dto.employee.position.PositionDto;
import by.bntu.salaryapp.domain.model.employee.Position;
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
public class PositionMapperImpl implements PositionMapper {

    @Override
    public PositionDto toDto(Position position) {
        if ( position == null ) {
            return null;
        }

        PositionDto positionDto = new PositionDto();

        positionDto.setCreatedBy( positionCreatedById( position ) );
        positionDto.setUpdatedBy( positionUpdatedById( position ) );
        positionDto.setCreatedAt( position.getCreatedDate() );
        positionDto.setId( position.getId() );
        positionDto.setTitle( position.getTitle() );
        positionDto.setUpdatedAt( position.getUpdatedAt() );

        return positionDto;
    }

    @Override
    public Position toEntity(PositionDto dto) {
        if ( dto == null ) {
            return null;
        }

        Position position = new Position();

        position.setTitle( dto.getTitle() );

        return position;
    }

    @Override
    public void updateFromDto(PositionDto dto, Position entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
    }

    private UUID positionCreatedById(Position position) {
        User createdBy = position.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        return createdBy.getId();
    }

    private UUID positionUpdatedById(Position position) {
        User updatedBy = position.getUpdatedBy();
        if ( updatedBy == null ) {
            return null;
        }
        return updatedBy.getId();
    }
}
