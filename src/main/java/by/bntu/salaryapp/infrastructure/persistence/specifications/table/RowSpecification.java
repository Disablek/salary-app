package by.bntu.salaryapp.infrastructure.persistence.specifications.table;

import by.bntu.salaryapp.application.dto.table.row.RowFilterDto;
import by.bntu.salaryapp.domain.model.employee.Employee;
import by.bntu.salaryapp.domain.model.table.Cell;
import by.bntu.salaryapp.domain.model.table.DataTable;
import by.bntu.salaryapp.domain.model.table.Row;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record RowSpecification(RowFilterDto filter) implements Specification<Row> {
    @Override
    public Predicate toPredicate(@NonNull Root<Row> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (filter == null) { return criteriaBuilder.conjunction();}

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
        }
        if (filter.getTableId() != null) {
            Join<Row, DataTable> join = root.join("tables", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id"), filter.getTableId()));
        }
        if (filter.getEmployeeId() != null) {
            Join<Row, Employee> join = root.join("employees", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id"), filter.getEmployeeId()));
        }
        if (filter.getCellsId() != null && !filter.getCellsId().isEmpty()) {
            Join<Row, Cell> join = root.join("cells", JoinType.LEFT);
            predicates.add(join.get("id").in(filter.getCellsId()));
        }
        if (filter .getUpdatedBy() != null) {
            Join<Row, User> join = root.join("updatedBy", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("updatedBy"), filter.getUpdatedBy()));
        }
        if (filter.getCreatedBy() != null) {
            Join<Row, User> join = root.join("createdBy", JoinType.INNER);
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
