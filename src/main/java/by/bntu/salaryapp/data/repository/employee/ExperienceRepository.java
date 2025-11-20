package by.bntu.salaryapp.data.repository.employee;

import by.bntu.salaryapp.data.model.employee.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, UUID> {
     Optional<Experience> findExperienceByTitle(String title);
}
