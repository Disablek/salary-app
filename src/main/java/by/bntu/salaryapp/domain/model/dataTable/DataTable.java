package by.bntu.salaryapp.domain.model.dataTable;

import by.bntu.salaryapp.domain.model.BaseAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Table(name= "tables")
public class DataTable extends BaseAuditingEntity {
    @NotNull
    private String name;

    @NotEmpty
    private String description;

    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Column> columns = new HashSet<>();

    @OneToMany(mappedBy = "mainTable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Row> rows = new HashSet<>();
}
