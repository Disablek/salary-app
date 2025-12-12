package by.bntu.salaryapp.infrastructure.persistence.specifications.employee;

import by.bntu.salaryapp.application.dto.employee.employee.EmployeeFilterDto;
import by.bntu.salaryapp.domain.model.employee.*;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record EmployeeFilterSpecification(EmployeeFilterDto filter)
        implements Specification<Employee> {

    @Override
    public Predicate toPredicate(@NonNull Root<Employee> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (filter == null) { return criteriaBuilder.conjunction();}

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
        }
        if (filter.getFirstName() != null) {
            predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + filter.getFirstName().toLowerCase() + "%"));
        }
        if (filter.getLastName() != null) {
            predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + filter.getLastName().toLowerCase() + "%"));
        }
        if (filter.getSurName() != null) {
            predicates.add(criteriaBuilder.like(root.get("surName"), "%" + filter.getSurName().toLowerCase() + "%"));
        }
        if (filter.getPosition_id() != null) {
            Join<Employee, Position> join = root.join("position", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id"), filter.getPosition_id()));
        }
        if (filter.getSubjects_id()!= null && !filter.getSubjects_id().isEmpty()) {
            Join<Employee, Subject> join = root.join("subjects", JoinType.INNER);
            predicates.add(join.get("id").in(filter.getSubjects_id()));
        }
        if (filter.getQualification_id() != null) {
            Join<Employee, Qualification> join = root.join("qualification", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id"), filter.getQualification_id()));
        }
        if (filter.getExperience_id() != null) {
            Join<Employee, Experience> join = root.join("experience", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id"), filter.getExperience_id()));
        }
        if (filter.getCreatedBy() != null) {
            Join<Employee, User> join = root.join("user", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id"), filter.getCreatedBy()));
        }
        if (filter.getUpdatedBy() != null) {
            Join<Employee, User> join = root.join("user", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id"), filter.getUpdatedBy()));
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

        if (predicates.isEmpty()) {
            return criteriaBuilder.conjunction(); }

        if (query.getResultType() != Long.class) {
            query.distinct(true);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    // Так называемая Фэбрик
    public static Specification<Employee> by(EmployeeFilterDto filterDto) {
        return new EmployeeFilterSpecification(filterDto);
    }
}
