package by.bntu.salaryapp.data.model;
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
