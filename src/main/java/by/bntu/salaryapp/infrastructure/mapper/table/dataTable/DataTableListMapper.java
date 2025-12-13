package by.bntu.salaryapp.infrastructure.mapper.table.dataTable;

import by.bntu.salaryapp.application.dto.table.dataTable.DataTableDto;
import by.bntu.salaryapp.domain.model.table.DataTable;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = DataTableMapper.class)
public interface DataTableListMapper {

    List<DataTableDto> toDtoList(List<DataTable> tables);

    List<DataTable> toEntityList(List<DataTableDto> dtos);
}
