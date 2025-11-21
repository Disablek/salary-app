package by.bntu.salaryapp.data.model.table;

import by.bntu.salaryapp.data.model.BaseAuditingEntity;
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
@jakarta.persistence.Table(name= "tables")
public class DataTable extends BaseAuditingEntity {
    @NotNull
    private String name;
    @NotEmpty
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "columns")
    private Set<Column> type = new HashSet<Column>();
}
