package by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.coefficient;

import by.bntu.salaryapp.domain.model.dataTable.coefficient.CoefficientRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CoefficientRuleRepository extends JpaRepository<CoefficientRule, UUID> {

    List<CoefficientRule> findAllByCoefficientId(UUID coefficientId);

    List<CoefficientRule> findByCoefficientId(UUID coefficientId);

    List<CoefficientRule> findByMatchValue(String matchValue);}
