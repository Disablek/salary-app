package by.bntu.salaryapp.infrastructure.persistence.repository.table;

import by.bntu.salaryapp.domain.model.table.DataTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<DataTable, Integer> {
}
