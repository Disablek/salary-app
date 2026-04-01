package by.bntu.salaryapp.infrastructure.mapper.dataTable.column;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.dataTable.column.ColumnDto;
import by.bntu.salaryapp.domain.model.dataTable.Cell;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import org.mapstruct.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(config = MapStructConfig.class)
public interface ColumnMapper {
    @Mapping(source = "mainTable.id", target = "dataTable_id")
    @Mapping(source = "coefficient.id", target = "coefficient_id")
    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(source = "updatedBy.id", target = "updatedBy")
    @Mapping(source = "createdDate", target = "createdAt")
    @Mapping(source = "cells", target = "cellsId")
    ColumnDto toDto(Column column);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mainTable", ignore = true)
    @Mapping(target = "coefficient", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cells", ignore = true)
    Column toEntity(ColumnDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mainTable", ignore = true)
    @Mapping(target = "coefficient", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cells", ignore = true)
    void updateFromDto(ColumnDto dto, @MappingTarget Column entity);

    default Set<UUID> mapCellsToIds(Set<Cell> cells) {
        if (cells == null) {
            return new java.util.HashSet<>();
        }
        return cells.stream()
                .map(Cell::getId)
                .collect(Collectors.toSet());
    }
}
