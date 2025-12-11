package by.bntu.salaryapp.domain.model.table;

import by.bntu.salaryapp.domain.model.BaseAuditingEntity;
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
    @JoinColumn(name = "row_id")
    private Row row;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private Column columnKey;

    @Nullable
    private String value;

    @Transient
    public void setValue(Object value) {
        this.value = value != null ? value.toString() : null;
    }
}
