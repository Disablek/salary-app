package by.bntu.salaryapp.domain.model.dataTable;

import by.bntu.salaryapp.domain.model.BaseAuditingEntity;
import by.bntu.salaryapp.domain.model.employee.Employee;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "row", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cell> cells = new HashSet<>();
}
