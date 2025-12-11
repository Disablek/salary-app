package by.bntu.salaryapp.infrastructure.persistence.repository.user;

import by.bntu.salaryapp.domain.model.user.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
}
