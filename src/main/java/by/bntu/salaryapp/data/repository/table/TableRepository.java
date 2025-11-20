package by.bntu.salaryapp.data.repository.table;

import by.bntu.salaryapp.data.model.table.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Table, Integer> {
}
