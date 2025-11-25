package by.bntu.salaryapp.domain.model.table.coefficient;

import by.bntu.salaryapp.domain.model.BaseEntity;
import by.bntu.salaryapp.domain.common.enums.CoefficientType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coefficients")
@EqualsAndHashCode(callSuper = true)
@Builder
public class Coefficient extends BaseEntity {
    @NotNull
    private String title;

    @NotEmpty
    private String description;

    @NotNull
    private Boolean isActive;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CoefficientType type;

    @NotNull
    private Set<CoefficientRule> coefficients = new HashSet<>();
}
