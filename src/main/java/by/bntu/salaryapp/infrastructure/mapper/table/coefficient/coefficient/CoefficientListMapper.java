package by.bntu.salaryapp.infrastructure.mapper.table.coefficient.coefficient;

import by.bntu.salaryapp.application.dto.table.coefficient.coefficient.CoefficientDto;
import by.bntu.salaryapp.domain.model.table.coefficient.Coefficient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CoefficientMapper.class)
public interface CoefficientListMapper {
    List<CoefficientDto> toDtoList(List<Coefficient> coefficients);

    List<Coefficient> toEntityList(List<CoefficientDto> dtos);
}
