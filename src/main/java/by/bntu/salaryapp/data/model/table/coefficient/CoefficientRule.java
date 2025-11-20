package by.bntu.salaryapp.data.model.table.coefficient;

import by.bntu.salaryapp.data.model.BaseEntity;
import by.bntu.salaryapp.data.model.enums.MatchType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coefficientsRule")
@Builder
public class CoefficientRule extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "coefficients")
    private Coefficient coefficient;

    @Nullable
    private Double minValue;
    @Nullable
    private Double maxValue;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MatchType matchKey;
}
