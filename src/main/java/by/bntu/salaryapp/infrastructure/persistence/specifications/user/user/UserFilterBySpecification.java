package by.bntu.salaryapp.infrastructure.persistence.specifications.user.user;

import by.bntu.salaryapp.application.dto.user.user.UserFilterDto;
import by.bntu.salaryapp.domain.model.user.Role;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record UserFilterBySpecification(UserFilterDto filter) implements Specification<User> {
    @Override
    public Predicate toPredicate(@NonNull Root<User> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (filter == null) { return criteriaBuilder.conjunction(); }

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
        }
        if (filter.getUsername() != null) {
            predicates.add(criteriaBuilder.like(root.get("username"), "%" + filter.getUsername() + "%"));
        }
        if (filter.getEmail() != null) {
            predicates.add(criteriaBuilder.like(root.get("email"), "%" + filter.getEmail() + "%"));
        }
        if (filter.getFirstName() != null) {
            predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + filter.getFirstName() + "%"));
        }
        if (filter.getSurName() != null) {
            predicates.add(criteriaBuilder.like(root.get("surName"), "%" + filter.getSurName() + "%"));
        }
        if (filter.getLastName() != null) {
            predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + filter.getLastName() + "%"));
        }
        if (filter.getRolesId() != null && !filter.getRolesId().isEmpty()) {
            Join<User, Role> join = root.join("roles", JoinType.INNER);
            predicates.add(join.get("id").in(filter.getRolesId()));
        }
        if (filter .getUpdatedBy() != null) {
            predicates.add(criteriaBuilder.equal(root.get("updatedBy"), filter.getUpdatedBy()));
        }
        if (filter.getCreatedBy() != null) {
            predicates.add(criteriaBuilder.equal(root.get("createdBy"), filter.getCreatedBy()));
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
