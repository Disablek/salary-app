package by.bntu.salaryapp.application.service.interfaces.dataTable.coefficient;

import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficient.CoefficientDto;
import by.bntu.salaryapp.domain.common.enums.CoefficientType;

import java.util.List;
import java.util.UUID;

public interface CoefficientService {
    CoefficientDto findById(UUID id);

    List<CoefficientDto> findAllActive();

    List<CoefficientDto> findAllByCoefficientType(CoefficientType coefficientType);

    CoefficientDto create(CoefficientDto coefficient);

    CoefficientDto update(CoefficientDto coefficient);

    void delete(UUID id);
}
