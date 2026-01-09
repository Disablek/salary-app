package by.bntu.salaryapp.infrastructure.persistence.specifications.dataTable.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.dataTable.DataTableFilterDto;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.DataTable;
import by.bntu.salaryapp.domain.model.dataTable.Row;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record DataTableFilterBySpecification(DataTableFilterDto filter) implements Specification<DataTable> {
    @Override
    public Predicate toPredicate(@NonNull Root<DataTable> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (filter == null) { return criteriaBuilder.conjunction(); }

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
        }
        if (filter.getName() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.getName() + "%"));
        }
        if (filter.getDescription() != null) {
            predicates.add(criteriaBuilder.like(root.get("description"), "%" + filter.getDescription() + "%"));
        }
        if (filter.getRows_id() != null && !filter.getRows_id().isEmpty()) {
            Join<DataTable, Row> join = root.join("rows", JoinType.LEFT);
            predicates.add(join.get("id").in(filter.getRows_id()));
        }
        if (filter.getColumns_id() != null && !filter.getColumns_id().isEmpty()) {
            Join<DataTable, Column> join = root.join("columns", JoinType.LEFT);
            predicates.add(join.get("id").in(filter.getColumns_id()));
        }
        if (filter .getUpdatedBy() != null) {
            Join<DataTable, User> join = root.join("updatedBy", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("updatedBy"), filter.getUpdatedBy()));
        }
        if (filter.getCreatedBy() != null) {
            Join<DataTable, User> join = root.join("createdBy", JoinType.INNER);
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
