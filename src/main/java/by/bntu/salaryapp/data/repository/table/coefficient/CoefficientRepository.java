package by.bntu.salaryapp.data.repository.table.coefficient;

import by.bntu.salaryapp.data.model.enums.CoefficientType;
import by.bntu.salaryapp.data.model.table.coefficient.Coefficient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface CoefficientRepository extends JpaRepository<Coefficient, UUID> {
    Set<Coefficient> findCoefficientByType(CoefficientType coefficientType);

    Set<Coefficient> findCoefficientByIsActive(Boolean isActive);
}

