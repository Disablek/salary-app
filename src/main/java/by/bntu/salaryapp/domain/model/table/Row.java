package by.bntu.salaryapp.domain.model.table;

import by.bntu.salaryapp.domain.model.BaseAuditingEntity;
import by.bntu.salaryapp.domain.model.employee.Employee;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@jakarta.persistence.Table(name = "rows")
public class Row extends BaseAuditingEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private DataTable table;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Nullable
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cell_id")
    private Set<Cell> cells = new HashSet<>();
}
