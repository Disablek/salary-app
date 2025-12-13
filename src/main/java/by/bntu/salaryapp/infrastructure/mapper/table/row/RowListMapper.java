package by.bntu.salaryapp.infrastructure.mapper.table.row;

import by.bntu.salaryapp.application.dto.table.row.RowDto;
import by.bntu.salaryapp.domain.model.table.Row;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = RowMapper.class)
public interface RowListMapper {
    List<RowDto> toDto(List<Row> rows);

    List<Row> toEntity(List<RowDto> row);
}
