package by.bntu.salaryapp.infrastructure.persistence.specifications.user.role;

import by.bntu.salaryapp.application.dto.user.role.RoleFilterDto;
import by.bntu.salaryapp.domain.model.user.Role;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record RoleFilterBySpecification(RoleFilterDto filter) implements Specification<Role> {
    @Override
    public Predicate toPredicate(@NonNull Root<Role> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (filter == null) {return criteriaBuilder.conjunction();}

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getId() != null){
            predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
        }
        if (filter.getName() != null){
            predicates.add(criteriaBuilder.equal(root.get("name"), "%" + filter.getName() + "%"));
        }
        if (filter .getUpdatedBy() != null) {
            Join<Role, User> join = root.join("updatedBy", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("updatedBy"), filter.getUpdatedBy()));
        }
        if (filter.getCreatedBy() != null) {
            Join<Role, User> join = root.join("createdBy", JoinType.INNER);
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
