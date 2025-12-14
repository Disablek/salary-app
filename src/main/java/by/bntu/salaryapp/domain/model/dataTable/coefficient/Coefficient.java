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

    // 3-й столбец: Столбец, который мы считаем
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_column_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Column targetColumn;

    // 2-й столбец: Столбец со строковым значением для сравнения (Source / Matcher)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_column_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Column sourceColumn;

    // 1-й столбец: Столбец с базовым значением, к которому применяем множитель
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_column_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Column baseColumn;

    @OneToMany(mappedBy = "coefficient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CoefficientRule> coefficientRules = new HashSet<>();
}