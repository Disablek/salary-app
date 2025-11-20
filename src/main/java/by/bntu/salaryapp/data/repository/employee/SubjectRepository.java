package by.bntu.salaryapp.data.repository.interfaces.employee;

import by.bntu.salaryapp.data.model.employee.Experience;
import by.bntu.salaryapp.data.model.employee.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubjectRepository extends JpaRepository<Experience, UUID> {
    Optional<Subject> findSubjectByTitle(String title);
}
