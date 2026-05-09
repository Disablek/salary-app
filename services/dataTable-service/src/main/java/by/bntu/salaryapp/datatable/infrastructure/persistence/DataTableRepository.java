package by.bntu.salaryapp.datatable.infrastructure.persistence;

import by.bntu.salaryapp.datatable.domain.model.DataTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DataTableRepository extends JpaRepository<DataTable, UUID> {
}