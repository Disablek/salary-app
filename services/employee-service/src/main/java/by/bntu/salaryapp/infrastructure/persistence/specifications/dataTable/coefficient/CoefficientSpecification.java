package by.bntu.salaryapp.infrastructure.persistence.specifications.dataTable.coefficient;

import by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficient.CoefficientFilterDto;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.CoefficientRule;
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
        if (filter.getTitle() != null) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + filter.getTitle() + "%"));
        }
        if (filter.getType() != null) {
            predicates.add(criteriaBuilder.equal(root.get("type"), filter.getType()));
        }
        if (filter.getCoefficientRulesIds() != null && !filter.getCoefficientRulesIds().isEmpty()) {
            Join<Coefficient, CoefficientRule> ruleJoin = root.join("coefficientRules", JoinType.INNER);
            predicates.add(ruleJoin.get("id").in(filter.getCoefficientRulesIds()));
        }
        if (filter.getSummationColumnsIds() != null && !filter.getSummationColumnsIds().isEmpty()) {
            Join<Coefficient, Column> sumColJoin = root.join("summationColumns", JoinType.INNER);
            predicates.add(sumColJoin.get("id").in(filter.getSummationColumnsIds()));
        }
        if (filter.getTargetColumnId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("targetColumn").get("id"), filter.getTargetColumnId()));
        }
        if (filter.getSourceColumnId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("sourceColumn").get("id"), filter.getSourceColumnId()));
        }
        if (filter.getBaseColumnId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("baseColumn").get("id"), filter.getBaseColumnId()));
        }
        if (filter.getUpdatedBy() != null) {
            Join<Coefficient, User> join = root.join("updatedBy", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("id"), filter.getUpdatedBy()));
        }
        if (filter.getCreatedBy() != null) {
            Join<Coefficient, User> join = root.join("createdBy", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("id"), filter.getCreatedBy()));
        }
        if (filter.getCreatedAtFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), filter.getCreatedAtFrom()));
        }
        if (filter.getCreatedAtTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), filter.getCreatedAtTo()));
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