package by.bntu.salaryapp.domain.model.user;


import by.bntu.salaryapp.domain.model.BaseAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name = "permissions")
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BaseAuditingEntity  implements GrantedAuthority{
    @NotNull
    @NotEmpty
    @Column(length = 16)
    private String name;

    private String description;

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Permission authority1 = (Permission) o;

        return name.equals(authority1.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permission='" + name + '\'' +
                '}';
    }
}
