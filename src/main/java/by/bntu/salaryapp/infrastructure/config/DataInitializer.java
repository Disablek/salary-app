package by.bntu.salaryapp.infrastructure.config;

import by.bntu.salaryapp.domain.model.user.Role;
import by.bntu.salaryapp.domain.model.user.User;
import by.bntu.salaryapp.infrastructure.persistence.repository.user.RoleRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.boot.CommandLineRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:superuser}")
    private String adminUsername;

    @Value("${app.admin.email:admin@example.com}")
    private String adminEmail;

    @Value("${app.admin.password:admin}")
    private String adminPassword;

    @Value("${app.admin.firstName:Admin}")
    private String adminFirstName;

    @Value("${app.admin.lastName:Admin}")
    private String adminLastName;

    @Value("${app.admin.surname:Admin}")
    private String adminSurname;

    @Override
    @Transactional
    public void run(String... args) {
        createRoleIfMissing("ROLE_USER");
        createRoleIfMissing("ROLE_ADMIN");
        createRoleIfMissing("ROLE_SUPERUSER");

        Role userRole = roleRepository.findByName("ROLE_USER").orElse(null);
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElse(null);
        Role superRole = roleRepository.findByName("ROLE_SUPERUSER").orElse(null);
        if (userRole == null || adminRole == null || superRole == null) {
            log.warn("Required roles not found after creation attempt");
            return;
        }

        migrateLegacyRoles(userRole, adminRole);

        boolean anySuper = userRepository.existsByRoleId(superRole.getId());
        if (!anySuper) {
            User admin = User.builder()
                    .username(adminUsername)
                    .email(adminEmail)
                    .firstName(adminFirstName)
                    .lastName(adminLastName)
                    .surname(adminSurname)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(superRole)
                    .build();
            userRepository.save(admin);
            log.info("Created superuser '{}' with email '{}'. Change default password immediately.", adminUsername, adminEmail);
        } else {
            log.info("Superuser already exists, skipping creation.");
        }
    }

    private void migrateLegacyRoles(Role userRole, Role adminRole) {
        migrateUsersFromRole("ROLE_NONE", userRole);
        migrateUsersFromRole("ROLE_CLIENT", userRole);
        migrateUsersFromRole("ROLE_PAYROLL_SPECIALIST", adminRole);
    }

    private void migrateUsersFromRole(String legacyRoleName, Role targetRole) {
        roleRepository.findByName(legacyRoleName).ifPresent(legacyRole -> {
            Set<User> users = userRepository.findByRole(legacyRole);
            if (users.isEmpty()) {
                return;
            }

            users.forEach(user -> user.setRole(targetRole));
            userRepository.saveAll(users);
            log.info("Migrated {} users from {} to {}", users.size(), legacyRoleName, targetRole.getName());
        });
    }

    private void createRoleIfMissing(String name) {
        if (!roleRepository.existsByName(name)) {
            Role r = new Role();
            r.setName(name);
            roleRepository.save(r);
            log.info("Created role: {}", name);
        }
    }
}
