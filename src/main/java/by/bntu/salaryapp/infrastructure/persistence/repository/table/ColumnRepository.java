package by.bntu.salaryapp.infrastructure.persistence.repository.table;

import by.bntu.salaryapp.domain.model.table.coefficient.Coefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import by.bntu.salaryapp.domain.model.table.Column;
import by.bntu.salaryapp.domain.model.table.DataTable;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ColumnRepository extends JpaRepository<Column, UUID> {
    Set<Column> findColumnByMainTable(DataTable mainTable);

    Optional<Column> findColumnByKey(String key);

    Set<Column> findColumnByCoefficient(Coefficient coefficient);
}
