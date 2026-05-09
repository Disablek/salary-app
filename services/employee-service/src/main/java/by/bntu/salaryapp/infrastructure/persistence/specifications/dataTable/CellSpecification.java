package by.bntu.salaryapp.infrastructure.persistence.specifications.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.cell.CellFilterDto;
import by.bntu.salaryapp.domain.model.employee.Employee;
import by.bntu.salaryapp.domain.model.dataTable.Cell;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.Row;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record CellSpecification(CellFilterDto filter) implements Specification<Employee> {
    @Override
    public Predicate toPredicate(@NonNull Root<Employee> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (filter == null) {
            return criteriaBuilder.conjunction(); }

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
        }
        if (filter.getRow_id() != null) {
            Join<Cell, Row> join = root.join("row", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id"), filter.getRow_id()));
        }
        if (filter.getColumn_id() != null) {
            Join<Cell, Column> join = root.join("column", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id"), filter.getColumn_id()));
        }
        if (filter.getValueFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("valueFrom"), filter.getValueFrom()));
        }
        if (filter.getValueTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("valueTo"), filter.getValueTo()));
        }
        if (filter .getUpdatedBy() != null) {
            Join<Cell, User> join = root.join("updatedBy", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("updatedBy"), filter.getUpdatedBy()));
        }
        if (filter.getCreatedBy() != null) {
            Join<Cell, User> join = root.join("createdBy", JoinType.INNER);
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
