package by.bntu.salaryapp.auth.application.service;

import by.bntu.salaryapp.auth.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishUserLoggedIn(Long userId, String email) {
        UserLoggedInEvent event = new UserLoggedInEvent(userId, email);
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_CREATED_QUEUE, event);
    }

    public record UserLoggedInEvent(Long userId, String email) {}
}