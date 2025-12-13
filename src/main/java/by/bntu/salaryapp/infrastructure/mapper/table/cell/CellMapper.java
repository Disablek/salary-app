package by.bntu.salaryapp.infrastructure.mapper.table.cell;

import by.bntu.salaryapp.application.dto.table.cell.CellDto;
import by.bntu.salaryapp.domain.model.table.Cell;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CellMapper {
    @Mapping(source = "row.id", target = "row_id")
    @Mapping(source = "column.id", target = "column_id")
    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(source = "updatedBy.id", target = "updatedBy")
    @Mapping(source = "createdDate", target = "createdAt")
    CellDto toDto(Cell cell);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "row", ignore = true)      // задаётся в сервисе
    @Mapping(target = "column", ignore = true)   // задаётся в сервисе
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Cell toEntity(CellDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "row", ignore = true)
    @Mapping(target = "column", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(CellDto dto, @MappingTarget Cell entity);
}
