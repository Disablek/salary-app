package by.bntu.salaryapp.infrastructure.persistence.repository.employee;

import by.bntu.salaryapp.domain.model.employee.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<Position, UUID> {
    Optional<Position> findPositionByTitle(String title);

    boolean existsByTitle(String title);
}
