package by.bntu.salaryapp.infrastructure.mapper.employee.position;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.employee.position.PositionDto;
import by.bntu.salaryapp.domain.model.employee.Position;
import org.mapstruct.*;

@Mapper(config = MapStructConfig.class)
public interface PositionMapper {
    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(source = "updatedBy.id", target = "updatedBy")
    @Mapping(source = "createdDate", target = "createdAt")
    PositionDto toDto(Position position);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Position toEntity(PositionDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(PositionDto dto, @MappingTarget Position entity);
}
