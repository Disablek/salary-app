package by.bntu.salaryapp.data.repository.employee;

import by.bntu.salaryapp.data.model.employee.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<Position, UUID> {
    Optional<Position> findPositionByCode(int positionCode);

    Optional<Position> findPositionByTitle(String title);
}
