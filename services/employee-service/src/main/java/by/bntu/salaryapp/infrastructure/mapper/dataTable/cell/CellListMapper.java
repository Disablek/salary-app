package by.bntu.salaryapp.infrastructure.mapper.dataTable.cell;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.dataTable.cell.CellDto;
import by.bntu.salaryapp.domain.model.dataTable.Cell;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = CellMapper.class)
public interface CellListMapper {

    List<CellDto> toDtoList(List<Cell> cells);

    List<Cell> toEntityList(List<CellDto> dtos);
}
