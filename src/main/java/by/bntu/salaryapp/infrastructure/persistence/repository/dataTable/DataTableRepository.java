package by.bntu.salaryapp.infrastructure.persistence.repository.dataTable;

import by.bntu.salaryapp.domain.model.dataTable.DataTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DataTableRepository extends JpaRepository<DataTable, UUID> {
}
