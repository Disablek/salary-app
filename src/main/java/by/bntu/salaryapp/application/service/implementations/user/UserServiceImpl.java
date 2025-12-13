package by.bntu.salaryapp.application.service.implementations.user;

import by.bntu.salaryapp.application.dto.user.user.UserDtoInput;
import by.bntu.salaryapp.application.dto.user.user.UserDtoOutput;
import by.bntu.salaryapp.application.service.interfaces.user.UserService;
import by.bntu.salaryapp.domain.model.user.Role;
import by.bntu.salaryapp.domain.model.user.User;
import by.bntu.salaryapp.infrastructure.mapper.user.user.UserListMapper;
import by.bntu.salaryapp.infrastructure.mapper.user.user.UserMapper;
import by.bntu.salaryapp.infrastructure.persistence.repository.user.RoleRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final UserListMapper userListMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDtoOutput create(UserDtoInput dto) {
        String email = dto.getEmail() != null ? dto.getEmail().trim() : null;
        String username = dto.getUsername() != null ? dto.getUsername().trim() : null;

        checkEmailUnique(email);
        checkUsernameUnique(username);

        User user = userMapper.toEntity(dto);
        user.setEmail(email);
        user.setUsername(username);

        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        UUID roleId = dto.getRoleId();

        if (roleId == null) {
            Role defaultRole = roleRepository.findByName("ROLE_NONE")
                    .orElseThrow(() -> new EntityNotFoundException("Default role ROLE_NONE not found"));
            assignSingleRoleToUser(user, defaultRole);
        } else {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));
            assignSingleRoleToUser(user, role);
        }

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    public UserDtoOutput update(UUID id, UserDtoInput dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        boolean isSuperUser = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_SUPERUSER"));

        if (!isSuperUser && !existingUser.getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("You can only update your own profile");
        }

        String newEmail = dto.getEmail() != null ? dto.getEmail().trim() : null;
        String newUsername = dto.getUsername() != null ? dto.getUsername().trim() : null;

        if (newEmail != null && !newEmail.equals(existingUser.getEmail())) {
            checkEmailUnique(newEmail);
            existingUser.setEmail(newEmail);
        }

        // TODO: изменять JWT токен
        // Важный момент: если пользователь меняет себе username, то при следующем запросе
        // токен может стать невалидным (зависит от реализации JWT), но в базе менять надо.
        if (newUsername != null && !newUsername.equals(existingUser.getUsername())) {
            checkUsernameUnique(newUsername);
            existingUser.setUsername(newUsername);
        }

        userMapper.updateFromDto(dto, existingUser);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        UUID roleId = dto.getRoleId();
        if (roleId != null) {
            if (!isSuperUser) {
                throw new AccessDeniedException("Only SUPERUSER can change roles");
            } else {
                Role role = roleRepository.findById(dto.getRoleId())
                        .orElseThrow(() -> new EntityNotFoundException("Role not found"));
                assignSingleRoleToUser(existingUser, role);
            }
        }

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        boolean isSuperUser = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_SUPERUSER"));

        if (!isSuperUser && !existingUser.getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("You can only delete your own profile");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDtoOutput getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    @Override
    @PreAuthorize("hasRole('SUPERUSER')")
    public List<UserDtoOutput> getAll() {
        List<User> users = userRepository.findAll();
        return userListMapper.toDtoList(users);
    }

    private void assignSingleRoleToUser(User user, Role role) {
        user.setRole(role);
    }

    private void checkEmailUnique(String email) {
        if (email != null && userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email '" + email + "' already exists");
        }
    }

    private void checkUsernameUnique(String username) {
        if (username != null && userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("User with username '" + username + "' already exists");
        }
    }

    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).
                orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

    }
}
