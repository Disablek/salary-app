package by.bntu.salaryapp.infrastructure.mapper.dataTable.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.dataTable.DataTableDto;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.DataTable;
import by.bntu.salaryapp.domain.model.dataTable.Row;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DataTableMapper {
    @Mapping(source = "columns", target = "columns_id")
    @Mapping(source = "rows", target = "rows_id")
    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(source = "updatedBy.id", target = "updatedBy")
    @Mapping(source = "createdDate", target = "createdAt")
    DataTableDto toDto(DataTable table);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "columns", ignore = true)
    @Mapping(target = "rows", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DataTable toEntity(DataTableDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "columns", ignore = true)
    @Mapping(target = "rows", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(DataTableDto dto, @MappingTarget DataTable entity);


    default Set<UUID> mapColumnsToIds(Set<Column> columns) {
        if (columns == null) {
            return new HashSet<>();
        }
        return columns.stream()
                .map(Column::getId)
                .collect(Collectors.toSet());
    }

    default Set<UUID> mapRowsToIds(Set<Row> rows) {
        if (rows == null) {
            return new HashSet<>();
        }
        return rows.stream()
                .map(Row::getId)
                .collect(Collectors.toSet());
    }
}
