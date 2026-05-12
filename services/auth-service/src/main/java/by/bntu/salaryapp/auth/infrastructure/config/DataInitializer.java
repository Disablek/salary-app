package by.bntu.salaryapp.auth.infrastructure.config;

import by.bntu.salaryapp.auth.domain.model.User;
import by.bntu.salaryapp.auth.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.email:admin@salary-app.local}")
    private String adminEmail;

    @Value("${app.admin.password:Admin@123}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(String... args) {
        boolean adminExists = userRepository.findByUsername(adminUsername).isPresent();
        if (!adminExists) {
            User admin = User.builder()
                    .username(adminUsername)
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(User.Role.SUPERUSER)
                    .enabled(true)
                    .build();
            userRepository.save(admin);
            log.info("Created auth-service admin user '{}' with email '{}'.", adminUsername, adminEmail);
        } else {
            log.info("Auth-service admin user '{}' already exists, skipping creation.", adminUsername);
        }
    }
}
