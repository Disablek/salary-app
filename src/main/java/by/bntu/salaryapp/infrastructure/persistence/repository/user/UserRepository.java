package by.bntu.salaryapp.infrastructure.persistence.repository.user;

import by.bntu.salaryapp.domain.model.user.Role;
import by.bntu.salaryapp.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    Set<User> findByRole(Role roles);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByRoleId(UUID roleId);
}
