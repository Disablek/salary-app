package by.bntu.salaryapp.presentation.rest.auth;

import by.bntu.salaryapp.application.dto.user.auth.LoginRequest;
import by.bntu.salaryapp.application.dto.user.auth.AuthResponse;
import by.bntu.salaryapp.application.service.implementations.user.JwtServiceImpl;
import by.bntu.salaryapp.domain.model.user.User;
import by.bntu.salaryapp.infrastructure.persistence.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login failed: user not found for email: {}", request.getEmail());
                    return new RuntimeException("Invalid credentials");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Login failed: invalid password for user: {}", request.getEmail());
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        log.info("User logged in successfully: {}", user.getUsername());
        
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
