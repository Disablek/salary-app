package by.bntu.salaryapp.infrastructure.persistence.repository.dataTable;

import by.bntu.salaryapp.domain.model.dataTable.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CellRepository extends JpaRepository<Cell, UUID> {

    // Найти все ячейки, принадлежащие конкретной строке
    List<Cell> findByRowId(UUID rowId);

    // Найти все ячейки, принадлежащие конкретному столбцу
    List<Cell> findByColumnId(UUID columnId);

    // Найти все ячейки в таблице (проход через сущность Row, у которой есть dataTable)
    // JPA умеет строить такие запросы по вложенным свойствам
    List<Cell> findByRow_DataTable_Id(UUID dataTableId);
}
