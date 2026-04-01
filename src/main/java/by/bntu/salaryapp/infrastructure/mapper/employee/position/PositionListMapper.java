package by.bntu.salaryapp.infrastructure.mapper.employee.position;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.employee.position.PositionDto;
import by.bntu.salaryapp.domain.model.employee.Position;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = PositionMapper.class)
public interface PositionListMapper {

    List<PositionDto> toDtoList(List<Position> positions);

    List<Position> toEntityList(List<PositionDto> dtos);
}
