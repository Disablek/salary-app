package by.bntu.salaryapp.domain.model.table;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private Set<Column> columns = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "row_id")
    private Set<Row> rows = new HashSet<>();
}
