package by.bntu.salaryapp.domain.model.employee;

import by.bntu.salaryapp.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subjects")
@Entity
@Builder
public class Subject extends BaseEntity {
    @NotEmpty
    @Column(nullable = false)
    private String title;
}
