package by.bntu.salaryapp.infrastructure.persistence.repository.table;

import by.bntu.salaryapp.domain.model.employee.Employee;
import by.bntu.salaryapp.domain.model.table.Row;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface RowRepository extends JpaRepository<Row, UUID> {
    Set<Row> findRowByTable(Row table);

    Set<Row> findRowByEmployee(Employee employee);
}
