package by.bntu.salaryapp.infrastructure.persistence.repository.dataTable;

import by.bntu.salaryapp.domain.model.dataTable.Row;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RowRepository extends JpaRepository<Row, UUID> {
    List<Row> findAllByTableId(UUID tableId);

    List<Row> findAllByEmployeeId(UUID employeeId);
}
