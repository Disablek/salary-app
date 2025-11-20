package by.bntu.salaryapp.data.model.user;

import by.bntu.salaryapp.data.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Value
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions")
@EqualsAndHashCode(callSuper = true)
public class Perimission extends BaseEntity {
    
}
