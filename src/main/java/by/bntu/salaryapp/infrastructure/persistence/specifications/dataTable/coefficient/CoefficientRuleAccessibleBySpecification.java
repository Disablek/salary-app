package by.bntu.salaryapp.infrastructure.persistence.specifications.dataTable.coefficient;

import by.bntu.salaryapp.domain.model.dataTable.coefficient.CoefficientRule;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

public record CoefficientRuleAccessibleBySpecification(User currentUser) implements Specification<CoefficientRule> {
    @Override
    public Predicate toPredicate(@NonNull Root<CoefficientRule> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (currentUser == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(false));
        }

        Predicate predicate = criteriaBuilder.conjunction();

        var authorities = currentUser.getAuthorities();

        boolean isSuperUser = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_SUPERUSER"));

        boolean isAdmin = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));


        if (isSuperUser && isAdmin) {
            return predicate;
        }
        else {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(false));
        }
    }
}
