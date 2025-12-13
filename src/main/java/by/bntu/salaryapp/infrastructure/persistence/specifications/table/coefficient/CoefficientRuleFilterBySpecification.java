package by.bntu.salaryapp.infrastructure.persistence.specifications.table.coefficient;

import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficientRule.CoefficientRuleFilterDto;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.CoefficientRule;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record CoefficientRuleFilterBySpecification(CoefficientRuleFilterDto filter)
        implements Specification<Coefficient> {
    @Override
    public Predicate toPredicate(@NonNull Root<Coefficient> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (filter == null) { return criteriaBuilder.conjunction(); }

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
        }
        if (filter.getCoefficientId() != null) {
            Join<CoefficientRule, Coefficient> join = root.join("coefficient", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("id"), filter.getCoefficientId()));
        }
        if (filter.getMatchValue() != null) {
            predicates.add(criteriaBuilder.equal(root.get("matchValue"), filter.getMatchValue()));
        }
        if (filter.getMultiplier() != null) {
            predicates.add(criteriaBuilder.equal(root.get("multiplier"), filter.getMultiplier()));
        }
        if (filter .getUpdatedBy() != null) {
            Join<CoefficientRule, User> join = root.join("updatedBy", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("updatedBy"), filter.getUpdatedBy()));
        }
        if (filter.getCreatedBy() != null) {
            Join<CoefficientRule, User> join = root.join("createdBy", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("createdBy"), filter.getCreatedBy()));
        }
        if (filter.getCreatedAtFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtFrom()));
        }
        if (filter.getCreatedAtTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtTo()));
        }
        if (filter.getUpdatedAtFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("updatedAt"), filter.getUpdatedAtFrom()));
        }
        if (filter.getUpdatedAtTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("updatedAt"), filter.getUpdatedAtTo()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
