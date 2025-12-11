package by.bntu.salaryapp.domain.model.table;

import by.bntu.salaryapp.domain.model.BaseAuditingEntity;
import by.bntu.salaryapp.domain.common.enums.ColumnDataType;
import by.bntu.salaryapp.domain.model.table.coefficient.Coefficient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    private String title;

    @Enumerated(EnumType.STRING)
    private ColumnDataType dataType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coefficient_id")
    private Coefficient coefficient;
}
