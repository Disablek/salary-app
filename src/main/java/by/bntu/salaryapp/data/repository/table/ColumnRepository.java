package by.bntu.salaryapp.data.repository.table;

import by.bntu.salaryapp.data.model.table.coefficient.Coefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import by.bntu.salaryapp.data.model.table.Column;
import by.bntu.salaryapp.data.model.table.Table;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ColumnRepository extends JpaRepository<Column, UUID> {
    Set<Column> findColumnByMainTable(Table mainTable);

    Optional<Column> findColumnByKey(String key);

    Set<Column> findColumnByCoefficient(Coefficient coefficient);
}
