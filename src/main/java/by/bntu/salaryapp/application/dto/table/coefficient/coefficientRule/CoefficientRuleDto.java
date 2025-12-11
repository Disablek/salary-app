package by.bntu.salaryapp.application.dto.table.coefficient.coefficientRule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoefficientRuleDto {
    private UUID id;

    private UUID coefficientId;

    private Integer matchValue;

    private BigDecimal multiplier;
}
