package by.bntu.salaryapp.infrastructure.persistence.repository.table.coefficient;

import by.bntu.salaryapp.domain.model.table.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.table.coefficient.CoefficientRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface CoefficientRuleRepository extends JpaRepository<CoefficientRule, UUID> {

    Set<CoefficientRule> findByCoefficient(Coefficient coefficient);

    Set<CoefficientRule> findByCoefficientId(UUID coefficientId);

    Set<CoefficientRule> findByMatchValue(Integer matchValue);}
