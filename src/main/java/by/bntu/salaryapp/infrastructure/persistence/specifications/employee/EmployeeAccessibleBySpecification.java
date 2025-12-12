package by.bntu.salaryapp.infrastructure.persistence.specifications.employee;

import by.bntu.salaryapp.application.dto.employee.employee.EmployeeFilterDto;
import by.bntu.salaryapp.domain.model.employee.Employee;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

public record EmployeeAccessibleBySpecification(EmployeeFilterDto filter, User currentUser)
        implements Specification<Employee> {

    @Override
    public Predicate toPredicate(@NonNull Root<Employee> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder cb) {
        if (currentUser == null) {
            return cb.conjunction();
        }
        Predicate predicate = cb.conjunction();

        var authorities = currentUser.getAuthorities();

        boolean isSuperUser = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_SUPERUSER"));

        boolean isAdmin = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isUser = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));

        if (isSuperUser)
            return predicate;

        if (isAdmin){
            return predicate;
        }

        if (isUser){
            return predicate;
        }

        return cb.disjunction();
    }
}