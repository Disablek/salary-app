package by.bntu.salaryapp.infrastructure.persistence.specifications.experience;

import by.bntu.salaryapp.application.dto.employee.experience.ExperienceDTO;
import by.bntu.salaryapp.application.dto.employee.experience.ExperienceFilterDto;
import by.bntu.salaryapp.domain.model.employee.Experience;
import by.bntu.salaryapp.domain.model.user.User;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record ExperienceFilterSpecification(ExperienceFilterDto filter) implements Specification<Experience> {

    @Override
    public Predicate toPredicate(@NonNull Root<Experience> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        if (filter == null) { return criteriaBuilder.conjunction(); }

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
        }
        if (filter.getTitle() != null) {
            predicates.add(criteriaBuilder.equal(root.get("title"), "%" + filter.getTitle().toLowerCase() + "%"));
        }
        if (filter.getCreatedBy() != null) {
            Join<Experience, User> join = root.join("createdBy", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("createdBy"), filter.getCreatedBy()));
        }
        if (filter.getUpdatedBy() != null) {
            Join<Experience, User> join = root.join("updatedBy", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(join.get("updatedBy"), filter.getUpdatedBy()));
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
