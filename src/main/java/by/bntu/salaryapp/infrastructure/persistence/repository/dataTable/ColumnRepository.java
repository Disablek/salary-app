package by.bntu.salaryapp.infrastructure.persistence.repository.dataTable;

import by.bntu.salaryapp.domain.model.dataTable.Column;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import by.bntu.salaryapp.domain.model.dataTable.DataTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ColumnRepository extends JpaRepository<Column, UUID> {

    List<Column> findAllByMainTable(DataTable mainTable);

    List<Column> findAllByMainTableId(UUID tableId);

    Optional<Column> findByKey(String key);

    List<Column> findAllByCoefficient(Coefficient coefficient);

    List<Column> findAllByMainTableIdAndActiveInPageIn(UUID tableId, Collection<Short> pages);
}