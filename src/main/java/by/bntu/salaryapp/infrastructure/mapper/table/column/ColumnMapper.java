package by.bntu.salaryapp.infrastructure.mapper.table.column;

import by.bntu.salaryapp.application.dto.table.column.ColumnDto;
import by.bntu.salaryapp.domain.model.table.Column;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ColumnMapper {
    @Mapping(source = "mainTable.id", target = "dataTable_id")
    @Mapping(source = "coefficient.id", target = "coefficient_id")
    @Mapping(source = "createdBy.id", target = "createdBy")
    @Mapping(source = "updatedBy.id", target = "updatedBy")
    @Mapping(source = "createdDate", target = "createdAt")
    ColumnDto toDto(Column column);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mainTable", ignore = true)
    @Mapping(target = "coefficient", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Column toEntity(ColumnDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mainTable", ignore = true)
    @Mapping(target = "coefficient", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(ColumnDto dto, @MappingTarget Column entity);
}
