package by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.coefficient;

import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.CoefficientRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface CoefficientRuleRepository extends JpaRepository<CoefficientRule, UUID> {

    Set<CoefficientRule> findByCoefficient(Coefficient coefficient);

    Set<CoefficientRule> findByCoefficientId(UUID coefficientId);

    Set<CoefficientRule> findByMatchValue(String matchValue);}
