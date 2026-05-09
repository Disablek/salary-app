package by.bntu.salaryapp.infrastructure.persistence.specifications.user.user;

import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

public record UserAccessibleBySpecification(User currentUser) implements Specification<User> {
    @Override
    public Predicate toPredicate(@NonNull Root<User> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (currentUser == null) {
            return criteriaBuilder.conjunction();
        }

        Predicate predicate = criteriaBuilder.conjunction();

        var authorities = currentUser.getAuthorities();

        boolean isSuperUser = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_SUPERUSER"));
        if (isSuperUser) {
            return predicate;
        }
        else {
            return criteriaBuilder.conjunction();
        }
    }
}
