package by.bntu.salaryapp.infrastructure.mapper.dataTable.coefficient.coefficient;

import by.bntu.salaryapp.infrastructure.mapper.MapStructConfig;
import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficient.CoefficientDto;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = CoefficientMapper.class)
public interface CoefficientListMapper {
    List<CoefficientDto> toDtoList(List<Coefficient> coefficients);

    List<Coefficient> toEntityList(List<CoefficientDto> dtos);
}
