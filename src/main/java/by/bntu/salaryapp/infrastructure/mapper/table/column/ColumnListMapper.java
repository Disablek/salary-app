package by.bntu.salaryapp.infrastructure.mapper.table.column;

import by.bntu.salaryapp.application.dto.table.column.ColumnDto;
import by.bntu.salaryapp.domain.model.table.Column;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ColumnMapper.class)
public interface ColumnListMapper {
    List<ColumnDto> toDtoList(List<Column> columns);

    List<Column> toEntityList(List<ColumnDto> dtos);
}
