package by.bntu.salaryapp.data.repository.table.coefficient;

import by.bntu.salaryapp.data.model.enums.MatchType;
import by.bntu.salaryapp.data.model.table.coefficient.Coefficient;
import by.bntu.salaryapp.data.model.table.coefficient.CoefficientRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface CoefficientRuleRepository extends JpaRepository<CoefficientRule, UUID> {
    Set<CoefficientRule> findCoefficientRuleByCoefficient(Coefficient coefficient);

    Set<CoefficientRule> findCoefficientRuleByMatchKey(MatchType matchKey);

}