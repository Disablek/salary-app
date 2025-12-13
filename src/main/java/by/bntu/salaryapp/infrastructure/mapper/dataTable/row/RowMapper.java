package by.bntu.salaryapp.infrastructure.mapper.dataTable.row;

import by.bntu.salaryapp.application.dto.dataTable.row.RowDto;
import by.bntu.salaryapp.domain.model.dataTable.Row;
import by.bntu.salaryapp.domain.model.dataTable.Cell;
import org.mapstruct.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RowMapper {
    @Mapping(source = "table.id", target = "tableId")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "cells", target = "cellsId")
    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(source = "updatedBy.id", target = "updatedBy")
    @Mapping(source = "createdDate", target = "createdAt")
    RowDto toDto(Row row);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "table", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "cells", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Row toEntity(RowDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "table", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "cells", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(RowDto dto, @MappingTarget Row entity);

    default Set<UUID> mapCellsToIds(Set<Cell> cells) {
        if (cells == null) {
            return new java.util.HashSet<>();
        }
        return cells.stream()
                .map(Cell::getId)
                .collect(Collectors.toSet());
    }
}
