package by.bntu.salaryapp.infrastructure.persistence.repository.user;

import by.bntu.salaryapp.domain.model.user.Role;
import by.bntu.salaryapp.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Set<User> findByRoles(Set<Role> roles);
}
