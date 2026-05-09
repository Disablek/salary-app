package by.bntu.salaryapp.infrastructure.persistence.specifications.dataTable;

import by.bntu.salaryapp.application.dto.dataTable.column.ColumnFilterDto;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.DataTable;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record ColumnSpecification(ColumnFilterDto filter) implements Specification<Column> {
    @Override
    public Predicate toPredicate(@NonNull Root<Column> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (filter == null) {return criteriaBuilder.conjunction();}

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getId() != null ) {
            predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
        }
        if (filter.getDataTable_id() != null ) {
            Join<Column, DataTable> join = root.join("dataTables", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id"), filter.getDataTable_id()));
        }
        if (filter.getTitle() != null ) {
            predicates.add(criteriaBuilder.equal(root.get("title"), filter.getTitle()));
        }
        if (filter.getKey() != null ) {
            predicates.add(criteriaBuilder.equal(root.get("key"), filter.getKey()));
        }
        if (filter.getDataType() != null ) {
            predicates.add(criteriaBuilder.equal(root.get("dataType"), filter.getDataType()));
        }
        if (filter.getCoefficient_id() != null ) {
            Join<Column, Coefficient>  join = root.join("coefficients", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("id"), filter.getCoefficient_id()));
        }
        if (filter .getUpdatedBy() != null) {
            Join<Column, User> join = root.join("updatedBy", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("updatedBy"), filter.getUpdatedBy()));
        }
        if (filter.getCreatedBy() != null) {
            Join<Column, User> join = root.join("createdBy", JoinType.INNER);
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
