package by.bntu.salaryapp.infrastructure.mapper.dataTable.dataTable;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.dataTable.dataTable.DataTableDto;
import by.bntu.salaryapp.domain.model.dataTable.DataTable;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = DataTableMapper.class)
public interface DataTableListMapper {

    List<DataTableDto> toDtoList(List<DataTable> tables);

    List<DataTable> toEntityList(List<DataTableDto> dtos);
}
