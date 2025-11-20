package by.bntu.salaryapp.data.repository.table;

import by.bntu.salaryapp.data.model.table.Cell;
import by.bntu.salaryapp.data.model.table.Column;
import by.bntu.salaryapp.data.model.table.Row;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CellRepository extends JpaRepository<Cell, UUID> {
    Set<Cell> findByRow(Row row);

    Optional<Cell> findByColumnKey(Column columnKey);
}
