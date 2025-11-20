package by.bntu.salaryapp.data.repository.interfaces.employee;

import by.bntu.salaryapp.data.model.employee.Experience;
import by.bntu.salaryapp.data.model.employee.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QualificationRepository extends JpaRepository<Experience, UUID> {
    Optional<Qualification> findQualificationByTitle(String title);
}
