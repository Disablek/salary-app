package by.bntu.salaryapp.infrastructure.mapper.dataTable.column;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.dataTable.column.ColumnDto;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = ColumnMapper.class)
public interface ColumnListMapper {
    List<ColumnDto> toDtoList(List<Column> columns);

    List<Column> toEntityList(List<ColumnDto> dtos);
}
