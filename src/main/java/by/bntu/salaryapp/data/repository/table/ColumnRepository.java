package by.bntu.salaryapp.data.repository.table.coefficient;

import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ColumnRepository extends JpaRepository<Column, UUID> {
    
}
