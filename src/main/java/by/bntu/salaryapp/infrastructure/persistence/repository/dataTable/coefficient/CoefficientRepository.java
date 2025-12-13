package by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.coefficient;

import by.bntu.salaryapp.domain.common.enums.CoefficientType;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface CoefficientRepository extends JpaRepository<Coefficient, UUID> {

    Set<Coefficient> findByType(CoefficientType type);

    Set<Coefficient> findByIsActive(Boolean isActive);
}
