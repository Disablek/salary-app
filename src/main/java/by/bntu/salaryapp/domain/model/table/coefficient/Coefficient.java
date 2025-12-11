package by.bntu.salaryapp.domain.model.table.coefficient;

import by.bntu.salaryapp.domain.model.BaseEntity;
import by.bntu.salaryapp.domain.common.enums.CoefficientType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coefficients")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coefficient extends BaseEntity {
    @NotNull
    private String title;

    private String description;

    @NotNull
    private Boolean isActive;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CoefficientType type;

    @OneToMany(mappedBy = "coefficient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CoefficientRule> rules = new HashSet<>();
}

