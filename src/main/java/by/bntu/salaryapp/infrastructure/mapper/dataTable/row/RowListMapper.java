package by.bntu.salaryapp.infrastructure.mapper.dataTable.row;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.dataTable.row.RowDto;
import by.bntu.salaryapp.domain.model.dataTable.Row;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = RowMapper.class)
public interface RowListMapper {
    List<RowDto> toDto(List<Row> rows);

    List<Row> toEntity(List<RowDto> row);
}
