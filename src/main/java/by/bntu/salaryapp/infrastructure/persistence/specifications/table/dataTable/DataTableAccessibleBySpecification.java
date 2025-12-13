package by.bntu.salaryapp.infrastructure.persistence.specifications.table.dataTable;

import by.bntu.salaryapp.domain.model.table.DataTable;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

public record DataTableAccessibleBySpecification(User currentUser) implements Specification<DataTable> {
    @Override
    public Predicate toPredicate(@NonNull Root<DataTable> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (currentUser == null) { return criteriaBuilder.isTrue(criteriaBuilder.literal(false)); }

        Predicate predicate = criteriaBuilder.conjunction();

        var authorities = currentUser.getAuthorities();

        boolean isNone = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_NONE"));
        if (!isNone) {
            return predicate;
        }
        else {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(false));
        }

    }
}
