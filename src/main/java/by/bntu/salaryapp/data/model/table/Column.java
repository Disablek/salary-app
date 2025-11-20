package by.bntu.salaryapp.data.model.table;

import by.bntu.salaryapp.data.model.BaseAuditingEntity;
import by.bntu.salaryapp.data.model.enums.ColumnDataType;
import by.bntu.salaryapp.data.model.table.coefficient.Coefficient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@jakarta.persistence.Table(name = "columns")
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Column extends BaseAuditingEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private by.bntu.salaryapp.data.model.table.Table mainTable;

    @NotNull
    private String key;

    private String title;

    @Enumerated(EnumType.STRING)
    private ColumnDataType dataType; //enum

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coefficient_id")
    private Coefficient coefficient;

}
