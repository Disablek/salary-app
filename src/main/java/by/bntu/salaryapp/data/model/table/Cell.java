package by.bntu.salaryapp.data.model.table;

import by.bntu.salaryapp.data.model.BaseAuditingEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@jakarta.persistence.Table(name = "cells")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cell extends BaseAuditingEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "rows")
    private Row row;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_key", referencedColumnName = "key", nullable = false)
    private Column columnKey;

    @Nullable
    private String value;

    @Transient
    public void setValue(Object value) {
        this.value = value != null ? value.toString() : null;
    }
}
