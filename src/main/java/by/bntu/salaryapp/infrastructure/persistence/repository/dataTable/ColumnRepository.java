package by.bntu.salaryapp.infrastructure.persistence.repository.dataTable;

import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.DataTable;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ColumnRepository extends JpaRepository<Column, UUID> {
    Set<Column> findColumnByMainTable(DataTable mainTable);

    Optional<Column> findColumnByKey(String key);

    Set<Column> findColumnByCoefficient(Coefficient coefficient);
}
