package by.bntu.salaryapp.domain.model.dataTable;

import by.bntu.salaryapp.domain.model.BaseAuditingEntity;
import by.bntu.salaryapp.domain.common.enums.ColumnDataType;
import by.bntu.salaryapp.domain.model.dataTable.coefficient.Coefficient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "columns")
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Column extends BaseAuditingEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private DataTable mainTable;

    @NotNull
    private String key;

    @NotNull
    @PositiveOrZero
    private Short activeInPage;

    private String title;

    @Enumerated(EnumType.STRING)
    private ColumnDataType dataType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coefficient_id")
    private Coefficient coefficient;

    @OneToMany(mappedBy = "column", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Cell> cells = new HashSet<>();
}
