package by.bntu.salaryapp.application.dto.table.column;

import by.bntu.salaryapp.domain.common.enums.ColumnDataType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnFilterDto {
    private UUID id;

    private UUID dataTable_id;

    private String key;

    private String title;

    @Enumerated(EnumType.STRING)
    private ColumnDataType dataType;

    private UUID coefficient_id;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    private LocalDateTime createdAtFrom;

    private LocalDateTime createdAtTo;

    private LocalDateTime updatedAtFrom;

    private LocalDateTime updatedAtTo;
}
