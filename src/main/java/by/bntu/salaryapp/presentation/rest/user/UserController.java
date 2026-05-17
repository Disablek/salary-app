package by.bntu.salaryapp.presentation.rest.user;

import by.bntu.salaryapp.presentation.common.user.UserEndpoint;
import by.bntu.salaryapp.presentation.common.ApiEndpoints;
import by.bntu.salaryapp.application.dto.user.user.UserDtoInput;
import by.bntu.salaryapp.application.dto.user.user.UserDtoOutput;
import by.bntu.salaryapp.application.dto.user.user.UserFilterDto;
import by.bntu.salaryapp.application.service.interfaces.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController implements UserEndpoint {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Override
    @GetMapping(ApiEndpoints.User.BASE)
    @PreAuthorize("hasRole('SUPERUSER')")
    public List<UserDtoOutput> getUsers(String search, Integer page, Integer size) {
        return userService.getAll();
    }

    @Override
    @PostMapping(ApiEndpoints.User.SEARCH)
    @PreAuthorize("hasRole('SUPERUSER')")
    public List<UserDtoOutput> searchUsers(@RequestBody(required = false) UserFilterDto filter) {
        return userService.getUsersByFilter(filter);
    }

    @Override
    @PostMapping(ApiEndpoints.Auth.REGISTER)
    public UserDtoOutput register(@Valid @RequestBody UserDtoInput user) {
        user.setRoleId(null);
        UserDtoOutput created = userService.create(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("../users/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return created;
    }

    @Override
    @PostMapping(ApiEndpoints.User.BASE)
    @PreAuthorize("hasRole('SUPERUSER')")
    public UserDtoOutput createUser(@Valid @RequestBody UserDtoInput user) {
        return userService.create(user);
    }

    @Override
    @PutMapping(ApiEndpoints.User.BY_ID)
    @PreAuthorize("hasRole('SUPERUSER')")
    public UserDtoOutput updateUser(@PathVariable UUID userId, @Valid @RequestBody UserDtoInput user) {
        return userService.update(userId, user);
    }

    @Override
    @PutMapping(ApiEndpoints.User.CHANGE_PASSWORD)
    public UserDtoOutput changeUserPassword(@PathVariable UUID userId,
                                            @RequestParam String oldPassword,
                                            @RequestParam String newPassword) {
        return userService.changePassword(userId, oldPassword, newPassword);
    }


    @Override
    @DeleteMapping(ApiEndpoints.User.BY_ID)
    @PreAuthorize("hasRole('SUPERUSER')")
    public void deleteUser(@PathVariable UUID userId) {
        userService.delete(userId);
    }

    // ----------------- get current user -----------------
    @Override
    @GetMapping(ApiEndpoints.User.CURRENT_USER)
    public UserDtoOutput getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            log.warn("No authenticated user in SecurityContext");
            throw new IllegalStateException("No authenticated user");
        }
        String username = authentication.getName();
        log.info("Getting current user: {}", username);
        log.debug("User authorities: {}", authentication.getAuthorities());

        UserDtoOutput user = userService.getByUsernameAsDto(username);
        log.info("Current user retrieved: {} with roleName: {}", user.getUsername(), user.getRoleName());
        return user;
    }

    // ----------------- get by id -----------------
    @Override
    @GetMapping(ApiEndpoints.User.BY_ID)
    @PreAuthorize("hasRole('SUPERUSER')")
    public UserDtoOutput getUserById(@PathVariable UUID userId) {
        return userService.getById(userId);
    }
}
