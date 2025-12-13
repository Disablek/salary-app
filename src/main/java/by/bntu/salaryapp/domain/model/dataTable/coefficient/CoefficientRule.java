package by.bntu.salaryapp.domain.model.dataTable.coefficient;

import by.bntu.salaryapp.domain.model.BaseAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "coefficient_rules")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoefficientRule extends BaseAuditingEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coefficient_id", nullable = false)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Coefficient coefficient;

    @NotNull
    private String matchValue;

    @NotNull
    private BigDecimal multiplier;
}

