package by.bntu.salaryapp.infrastructure.persistence.specifications.table.coefficient;

import by.bntu.salaryapp.application.dto.table.coefficient.coefficient.CoefficientFilterDto;
import by.bntu.salaryapp.domain.model.table.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.table.coefficient.CoefficientRule;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record CoefficientSpecification(CoefficientFilterDto filter) implements Specification<Coefficient> {
    @Override
    public Predicate toPredicate(@NonNull Root<Coefficient> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (filter == null) { return criteriaBuilder.conjunction(); }

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
        }
        if (filter.getIsActive() != null) {
            predicates.add(criteriaBuilder.equal(root.get("isActive"), filter.getIsActive()));
        }
        if (filter.getDescription() != null) {
            predicates.add(criteriaBuilder.like(root.get("description"), "%" + filter.getDescription() + "%"));
        }
        if (filter.getCoefficientRulesIds() != null && !filter.getCoefficientRulesIds().isEmpty()) {
            Join<Coefficient, CoefficientRule> ruleJoin = root.join("coefficientRules", JoinType.INNER);
            predicates.add(ruleJoin.get("id").in(filter.getCoefficientRulesIds()));
        }
        if (filter.getTitle() != null) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + filter.getTitle() + "%"));
        }
        if (filter.getType() != null) {
            predicates.add(criteriaBuilder.equal(root.get("type"), filter.getType()));
        }
        if (filter .getUpdatedBy() != null) {
            Join<Coefficient, User> join = root.join("updatedBy", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("updatedBy"), filter.getUpdatedBy()));
        }
        if (filter.getCreatedBy() != null) {
            Join<Coefficient, User> join = root.join("createdBy", JoinType.INNER);
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
