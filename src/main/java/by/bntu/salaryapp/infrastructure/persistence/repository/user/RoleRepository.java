package by.bntu.salaryapp.infrastructure.persistence.repository.user;

import by.bntu.salaryapp.domain.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

}
