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
    public static final String USER_CREATION_FAILED_QUEUE = "user.creation.failed.queue";

    public void startUserCreationSaga(User user) {
        try {
            // Publish user created event
            UserCreatedEvent event = new UserCreatedEvent(
                user.getId().toString(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
            );

            rabbitTemplate.convertAndSend(USER_CREATED_QUEUE, event);

            // In a real saga, we would wait for compensations or confirmations
            // For simplicity, we'll just publish the event

        } catch (Exception e) {
            // Publish failure event for compensation
            UserCreationFailedEvent failedEvent = new UserCreationFailedEvent(
                user.getId().toString(),
                user.getEmail(),
                e.getMessage()
            );

            rabbitTemplate.convertAndSend(USER_CREATION_FAILED_QUEUE, failedEvent);
            throw e;
        }
    }

    public record UserCreatedEvent(String userId, String email, String firstName, String lastName) {}
    public record UserCreationFailedEvent(String userId, String email, String reason) {}
}