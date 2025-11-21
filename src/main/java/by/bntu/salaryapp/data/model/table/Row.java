package by.bntu.salaryapp.data.model.table;

import by.bntu.salaryapp.data.model.BaseAuditingEntity;
import by.bntu.salaryapp.data.model.employee.Employee;
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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cells")
    private Set<Cell> cells = new HashSet<>();
}
