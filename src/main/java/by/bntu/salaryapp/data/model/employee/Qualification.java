package by.bntu.salaryapp.data.model.employee;

import by.bntu.salaryapp.data.model.BaseAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Table(name = "qualifications")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
public class Qualification extends BaseAuditingEntity {
    @NotEmpty
    @Column(nullable = false)
    private String title;
}
