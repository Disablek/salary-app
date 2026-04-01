package by.bntu.salaryapp.infrastructure.config;

import by.bntu.salaryapp.domain.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

    private static final Logger log = LoggerFactory.getLogger(AuditingConfig.class);

    @Bean
    public AuditorAware<User> auditorProvider() {
        return () -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                log.debug("No authentication found, auditor will be empty");
                return Optional.empty();
            }
            Object principal = auth.getPrincipal();
            if (principal instanceof User) {
                User user = (User) principal;
                log.debug("Auditor set to user: {}", user.getUsername());
                return Optional.of(user);
            }
            log.debug("Principal is not a User instance, auditor will be empty");
            return Optional.empty();
        };
    }
}
