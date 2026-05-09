package by.bntu.salaryapp.infrastructure.persistence.specifications.user.role;

import by.bntu.salaryapp.domain.model.user.Role;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

public record RoleAccessibleBySpecification(User currentUser) implements Specification<Role> {
    @Override
    public Predicate toPredicate(@NonNull Root<Role> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (currentUser == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(false));
        }
        Predicate predicate = criteriaBuilder.conjunction();

        var authorities = currentUser.getAuthorities();

        boolean isSuperUser = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_SUPERUSER"));
        if (isSuperUser) {
            return predicate;
        }
        else {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(false));
        }
    }
}
