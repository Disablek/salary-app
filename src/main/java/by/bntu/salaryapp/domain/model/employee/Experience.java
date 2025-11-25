package by.bntu.salaryapp.domain.model.employee;
import by.bntu.salaryapp.domain.model.BaseAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "experiences")
@Builder
public class Experience extends BaseAuditingEntity {
    @NotEmpty
    @Column(nullable = false)
    private String title;
}
