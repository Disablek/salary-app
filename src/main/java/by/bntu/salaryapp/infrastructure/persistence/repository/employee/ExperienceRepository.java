package by.bntu.salaryapp.infrastructure.persistence.repository.employee;

import by.bntu.salaryapp.domain.model.employee.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, UUID>,
        JpaSpecificationExecutor<Experience> {
    Optional<Experience> findExperienceByTitle(String title);

    boolean existsByTitle(String title);
}
