package by.bntu.salaryapp.infrastructure.persistence.repository.employee;

import by.bntu.salaryapp.domain.model.employee.Experience;
import by.bntu.salaryapp.domain.model.employee.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QualificationRepository extends JpaRepository<Experience, UUID> {
    Optional<Qualification> findQualificationByTitle(String title);
}
