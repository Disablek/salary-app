package by.bntu.salaryapp.presentation.rest.user;

import by.bntu.salaryapp.presentation.common.user.UserEndpoint;
import by.bntu.salaryapp.presentation.common.ApiEndpoints;
import by.bntu.salaryapp.application.dto.user.user.UserDtoInput;
import by.bntu.salaryapp.application.dto.user.user.UserDtoOutput;
import by.bntu.salaryapp.application.dto.user.user.UserFilterDto;
import by.bntu.salaryapp.application.service.interfaces.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    private final UserService userService;

    @Override
    @GetMapping(ApiEndpoints.User.BASE)
    public List<UserDtoOutput> getUsers(String search, Integer page, Integer size) {
        return userService.getAll();
    }

    @Override
    @PostMapping(ApiEndpoints.User.SEARCH)
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
    public UserDtoOutput createUser(@Valid @RequestBody UserDtoInput user) {
        return userService.create(user);
    }

    @Override
    @PutMapping(ApiEndpoints.User.BY_ID)
    public UserDtoOutput updateUser(@PathVariable UUID userId, @Valid @RequestBody UserDtoInput user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isSuper = isSuperUser(authentication);
        if (!isSuper) {
            user.setRoleId(null);
        }
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
    public void deleteUser(@PathVariable UUID userId) {
        userService.delete(userId);
    }

    // ----------------- get by id -----------------
    @Override
    @GetMapping(ApiEndpoints.User.BY_ID)
    public UserDtoOutput getUserById(@PathVariable UUID userId) {
        return userService.getById(userId);
    }

    // ----------------- get current user -----------------
    @Override
    @GetMapping(ApiEndpoints.User.CURRENT_USER)
    public UserDtoOutput getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new IllegalStateException("No authenticated user");
        }
        String username = authentication.getName();

        // Recommended to add userService.getByUsername(username) for efficient lookup.
        // Temporary fallback: linear search (works but inefficient)
        List<UserDtoOutput> all = userService.getAll();
        return all.stream()
                .filter(u -> username.equals(u.getUsername()) || username.equals(u.getEmail()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Current user not found"));
    }

    // ----------------- helpers -----------------
    private boolean isSuperUser(Authentication authentication) {
        if (authentication == null) return false;
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_SUPERUSER"));
    }
}
