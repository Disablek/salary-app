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
        createRoleIfMissing("ROLE_NONE");
        createRoleIfMissing("ROLE_USER");
        createRoleIfMissing("ROLE_ADMIN");
        createRoleIfMissing("ROLE_SUPERUSER");

        Role superRole = roleRepository.findByName("ROLE_SUPERUSER").orElse(null);
        if (superRole == null) {
            log.warn("ROLE_SUPERUSER not found after creation attempt");
            return;
        }

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

    private void createRoleIfMissing(String name) {
        if (!roleRepository.existsByName(name)) {
            Role r = new Role();
            r.setName(name);
            roleRepository.save(r);
            log.info("Created role: {}", name);
        }
    }
}
