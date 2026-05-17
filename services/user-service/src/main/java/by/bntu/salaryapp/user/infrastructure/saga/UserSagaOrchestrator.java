package by.bntu.salaryapp.user.infrastructure.saga;

import by.bntu.salaryapp.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSagaOrchestrator {

    private final RabbitTemplate rabbitTemplate;

    public static final String USER_CREATED_QUEUE = "user.created.queue";
    public static final String USER_UPDATED_QUEUE = "user.updated.queue";
    public static final String USER_DELETED_QUEUE = "user.deleted.queue";
    public static final String USER_CREATION_FAILED_QUEUE = "user.creation.failed.queue";

    public void startUserCreationSaga(User user) {
        try {
            UserCreatedEvent event = new UserCreatedEvent(
                user.getId().toString(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().name(),
                user.getFirstName(),
                user.getLastName()
            );

            rabbitTemplate.convertAndSend(USER_CREATED_QUEUE, event);

        } catch (Exception e) {
            UserCreationFailedEvent failedEvent = new UserCreationFailedEvent(
                user.getId().toString(),
                user.getEmail(),
                e.getMessage()
            );

            rabbitTemplate.convertAndSend(USER_CREATION_FAILED_QUEUE, failedEvent);
            throw e;
        }
    }

    public void publishUserUpdated(User user, String previousEmail) {
        UserUpdatedEvent event = new UserUpdatedEvent(
                user.getId().toString(),
                previousEmail,
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().name()
        );

        rabbitTemplate.convertAndSend(USER_UPDATED_QUEUE, event);
    }

    public void publishUserDeleted(User user) {
        UserDeletedEvent event = new UserDeletedEvent(
                user.getId().toString(),
                user.getEmail(),
                user.getUsername()
        );

        rabbitTemplate.convertAndSend(USER_DELETED_QUEUE, event);
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
    public record UserCreationFailedEvent(String userId, String email, String reason) {}
}
