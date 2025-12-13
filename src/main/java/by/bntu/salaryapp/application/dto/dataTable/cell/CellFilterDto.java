package by.bntu.salaryapp.application.dto.dataTable.cell;

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
public class CellFilterDto {
    private UUID id;

    private UUID row_id;

    private UUID column_id;

    private String valueFrom;

    private String valueTo;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    private LocalDateTime createdAtFrom;

    private LocalDateTime createdAtTo;

    private LocalDateTime updatedAtFrom;

    private LocalDateTime updatedAtTo;
}
