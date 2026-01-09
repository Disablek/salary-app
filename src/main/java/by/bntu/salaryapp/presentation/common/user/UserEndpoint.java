package by.bntu.salaryapp.presentation.common.user;

import by.bntu.salaryapp.presentation.common.ApiEndpoints;
import by.bntu.salaryapp.application.dto.user.user.UserDtoInput;
import by.bntu.salaryapp.application.dto.user.user.UserDtoOutput;
import by.bntu.salaryapp.application.dto.user.user.UserFilterDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface UserEndpoint {

    /**
     * GET /api/users
     * Только SUPERUSER — возвращает всех (без фильтра).
     */
    @GetMapping(ApiEndpoints.User.BASE)
    List<UserDtoOutput> getUsers(@RequestParam(value = "search", required = false) String search,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "size", required = false) Integer size);

    /**
     * POST /api/users/search
     */
    @PostMapping(ApiEndpoints.User.SEARCH)
    List<UserDtoOutput> searchUsers(@RequestBody(required = false) UserFilterDto filter);

    @PostMapping(ApiEndpoints.Auth.REGISTER)
    UserDtoOutput register(@Valid @RequestBody UserDtoInput user);

    @PostMapping(ApiEndpoints.User.BASE)
    UserDtoOutput createUser(@Valid @RequestBody UserDtoInput user);

    @PutMapping(ApiEndpoints.User.BY_ID)
    UserDtoOutput updateUser(@PathVariable UUID userId, @Valid @RequestBody UserDtoInput user);

    @PutMapping(ApiEndpoints.User.CHANGE_PASSWORD)
    UserDtoOutput changeUserPassword(@PathVariable UUID userId,
                                     @RequestParam String oldPassword,
                                     @RequestParam String newPassword);

    @DeleteMapping(ApiEndpoints.User.BY_ID)
    void deleteUser(@PathVariable UUID userId);

    @GetMapping(ApiEndpoints.User.BY_ID)
    UserDtoOutput getUserById(@PathVariable UUID userId);

    @GetMapping(ApiEndpoints.User.CURRENT_USER)
    UserDtoOutput getCurrentUser();
}
