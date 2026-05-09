package by.bntu.salaryapp.auth.presentation.rest;

import by.bntu.salaryapp.auth.application.service.JwtService;
import by.bntu.salaryapp.auth.application.service.UserService;
import by.bntu.salaryapp.auth.domain.model.User;
import by.bntu.salaryapp.auth.presentation.dto.AuthResponse;
import by.bntu.salaryapp.auth.presentation.dto.LoginRequest;
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

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        log.info("Login attempt for email: {}", request.email());

        User user = userService.loadUserByEmail(request.email());

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            log.warn("Login failed for email: {}", request.email());
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        log.info("User logged in successfully: {}", user.getUsername());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}