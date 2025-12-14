package by.bntu.salaryapp.infrastructure.persistence.repository.dataTable;

import by.bntu.salaryapp.domain.model.dataTable.Cell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface CellRepository extends JpaRepository<Cell, UUID> {
    List<Cell> findByRowId(UUID rowId);

    List<Cell> findByColumnId(UUID columnId);

    List<Cell> findByRow_Table_Id(UUID dataTableId);

    List<Cell> findByColumn_MainTable_IdAndColumn_ActiveInPageIn(UUID tableId, Collection<Short> pages);
}
