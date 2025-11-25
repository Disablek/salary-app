package by.bntu.salaryapp.infrastructure.persistence.repository.table.coefficient;

import by.bntu.salaryapp.domain.common.enums.MatchType;
import by.bntu.salaryapp.domain.model.table.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.table.coefficient.CoefficientRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface CoefficientRuleRepository extends JpaRepository<CoefficientRule, UUID> {
    Set<CoefficientRule> findCoefficientRuleByCoefficient(Coefficient coefficient);

    Set<CoefficientRule> findCoefficientRuleByMatchKey(MatchType matchKey);

}