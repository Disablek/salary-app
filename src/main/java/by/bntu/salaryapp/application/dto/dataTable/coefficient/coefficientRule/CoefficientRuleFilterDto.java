package by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficientRule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoefficientRuleFilterDto {
    private UUID id;

    private UUID coefficientId;

    private Integer matchValue;

    private BigDecimal multiplier;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    private LocalDateTime createdAtFrom;

    private LocalDateTime createdAtTo;

    private LocalDateTime updatedAtFrom;

    private LocalDateTime updatedAtTo;
}
