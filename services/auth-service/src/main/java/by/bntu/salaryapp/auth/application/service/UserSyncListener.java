package by.bntu.salaryapp.auth.application.service;

import by.bntu.salaryapp.auth.domain.model.User;
import by.bntu.salaryapp.auth.infrastructure.config.RabbitMQConfig;
import by.bntu.salaryapp.auth.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserSyncListener {

    private static final Logger log = LoggerFactory.getLogger(UserSyncListener.class);

    private final UserRepository userRepository;

    @RabbitListener(queues = RabbitMQConfig.USER_CREATED_QUEUE)
    @Transactional
    public void handleUserCreated(UserCreatedEvent event) {
        User user = userRepository.findByEmail(event.email())
                .orElseGet(() -> User.builder().enabled(true).build());

        applyUserSnapshot(user, event.email(), event.username(), event.password(), event.role());
        userRepository.save(user);
        log.info("Synchronized created user '{}' into auth-service.", event.email());
    }

    @RabbitListener(queues = RabbitMQConfig.USER_UPDATED_QUEUE)
    @Transactional
    public void handleUserUpdated(UserUpdatedEvent event) {
        Optional<User> existingUser = userRepository.findByEmail(event.email());
        if (existingUser.isEmpty() && event.previousEmail() != null && !event.previousEmail().isBlank()) {
            existingUser = userRepository.findByEmail(event.previousEmail());
        }

        User user = existingUser.orElseGet(() -> User.builder().enabled(true).build());
        applyUserSnapshot(user, event.email(), event.username(), event.password(), event.role());
        userRepository.save(user);
        log.info("Synchronized updated user '{}' into auth-service.", event.email());
    }

    @RabbitListener(queues = RabbitMQConfig.USER_DELETED_QUEUE)
    @Transactional
    public void handleUserDeleted(UserDeletedEvent event) {
        userRepository.findByEmail(event.email()).ifPresent(user -> {
            userRepository.delete(user);
            log.info("Removed deleted user '{}' from auth-service.", event.email());
        });
    }

    private void applyUserSnapshot(User user, String email, String username, String encodedPassword, String role) {
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole(resolveRole(role));
        user.setEnabled(true);
    }

    private User.Role resolveRole(String role) {
        if (role == null || role.isBlank()) {
            return User.Role.CLIENT;
        }

        return switch (role.trim().toUpperCase()) {
            case "USER", "CLIENT" -> User.Role.CLIENT;
            case "ADMIN" -> User.Role.ADMIN;
            case "SUPERUSER" -> User.Role.SUPERUSER;
            default -> throw new IllegalArgumentException("Unsupported role for auth sync: " + role);
        };
    }

    public record UserCreatedEvent(
            String userId,
            String email,
            String username,
            String password,
            String role,
            String firstName,
            String lastName
    ) {}

    public record UserUpdatedEvent(
            String userId,
            String previousEmail,
            String email,
            String username,
            String password,
            String role
    ) {}

    public record UserDeletedEvent(String userId, String email, String username) {}
}
