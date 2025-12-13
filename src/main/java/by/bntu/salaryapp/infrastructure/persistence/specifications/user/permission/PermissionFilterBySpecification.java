package by.bntu.salaryapp.infrastructure.persistence.specifications.user.permission;

import by.bntu.salaryapp.application.dto.user.permission.PermissionFilterDto;
import by.bntu.salaryapp.domain.model.user.Permission;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record PermissionFilterBySpecification(PermissionFilterDto filter) implements Specification<Permission> {
    @Override
    public Predicate toPredicate(@NonNull Root<Permission> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (filter == null) {return criteriaBuilder.conjunction();}

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
        }
        if (filter.getName() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.getName() + "%"));
        }
        if (filter.getDescription() != null) {
            predicates.add(criteriaBuilder.like(root.get("description"), "%" + filter.getDescription() + "%"));
        }
        if (filter .getUpdatedBy() != null) {
            Join<Permission, User> join = root.join("updatedBy", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("updatedBy"), filter.getUpdatedBy()));
        }
        if (filter.getCreatedBy() != null) {
            Join<Permission, User> join = root.join("createdBy", JoinType.INNER);
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
