package by.bntu.salaryapp.data.repository.employee;

import by.bntu.salaryapp.data.model.employee.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Set<Employee> findEmployeeByPosition(Position position);

    Set<Employee> findEmployeeByQualification(Qualification qualification);

    Set<Employee> findEmployeeByExperience(Experience experience);

    Set<Employee> findEmployeeBySubject(Set<Subject> subject);
}
