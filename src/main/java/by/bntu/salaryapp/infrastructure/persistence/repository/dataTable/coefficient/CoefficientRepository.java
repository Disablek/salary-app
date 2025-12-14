package by.bntu.salaryapp.infrastructure.persistence.repository.dataTable.coefficient;

import by.bntu.salaryapp.domain.common.enums.CoefficientType;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CoefficientRepository extends JpaRepository<Coefficient, UUID> {

    @Query("SELECT c FROM Coefficient c WHERE c.targetColumn.mainTable.id = :tableId AND c.isActive = true")
    List<Coefficient> findActiveByTableId(@Param("tableId") UUID tableId);

    List<Coefficient> findAllByIsActiveTrue();

    List<Coefficient> findAllByType(CoefficientType type);

    boolean existsByTitle(String title);
}
