package by.bntu.salaryapp.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Role implements GrantedAuthority {

    @Id
    @Column(length = 16)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Role authority1 = (Role) o;

        return name.equals(authority1.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Role{" +
                "role='" + name + '\'' +
                '}';
    }
}
