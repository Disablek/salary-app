package by.bntu.salaryapp.infrastructure.mapper.dataTable.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.dataTable.DataTableDto;
import by.bntu.salaryapp.domain.model.dataTable.DataTable;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = DataTableMapper.class)
public interface DataTableListMapper {

    List<DataTableDto> toDtoList(List<DataTable> tables);

    List<DataTable> toEntityList(List<DataTableDto> dtos);
}
