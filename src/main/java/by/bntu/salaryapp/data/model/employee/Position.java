package by.bntu.salaryapp.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "positions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Position extends BaseAuditingEntity{
    @NotEmpty
    @Column(nullable = false)
    private String title;
    @NotEmpty
    @Column(nullable = false)
    private Integer code;

}
