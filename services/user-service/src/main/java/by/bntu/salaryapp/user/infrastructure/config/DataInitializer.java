package by.bntu.salaryapp.user.infrastructure.config;

import by.bntu.salaryapp.user.domain.model.User;
import by.bntu.salaryapp.user.infrastructure.persistence.UserRepository;
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
        boolean adminExists = userRepository.findByUsername(adminUsername).isPresent();
        if (!adminExists) {
            User admin = User.builder()
                    .username(adminUsername)
                    .email(adminEmail)
                    .firstName(adminFirstName)
                    .lastName(adminLastName)
                    .surname(adminSurname)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(User.Role.SUPERUSER)
                    .build();
            userRepository.save(admin);
            log.info("Created superuser '{}' with email '{}'. Change default password immediately.", adminUsername, adminEmail);
        } else {
            log.info("Superuser already exists, skipping creation.");
        }
    }
}
