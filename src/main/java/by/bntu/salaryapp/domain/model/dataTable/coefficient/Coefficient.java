package by.bntu.salaryapp.domain.model.dataTable.coefficient;

import by.bntu.salaryapp.domain.model.BaseAuditingEntity;
import by.bntu.salaryapp.domain.common.enums.CoefficientType;
import by.bntu.salaryapp.domain.model.dataTable.Column;
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
public class Coefficient extends BaseAuditingEntity {
    @NotNull
    private String title;

    private String description;

    @NotNull
    private Boolean isActive;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CoefficientType type;

    // --- MULTIPLIER ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_column_id")
    private Column targetColumn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_column_id")
    private Column sourceColumn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_column_id")
    private Column baseColumn;

    @OneToMany(mappedBy = "coefficient", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<CoefficientRule> coefficientRules = new HashSet<>();

    // --- SUMMATION ---
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "coefficient_summation_columns",
            joinColumns = @JoinColumn(name = "coefficient_id"),
            inverseJoinColumns = @JoinColumn(name = "column_id")
    )
    @Builder.Default
    private Set<Column> summationColumns = new HashSet<>();
}