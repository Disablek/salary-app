package by.bntu.salaryapp.application.service.implementations.user;

import by.bntu.salaryapp.application.dto.user.user.UserDtoInput;
import by.bntu.salaryapp.application.dto.user.user.UserDtoOutput;
import by.bntu.salaryapp.application.dto.user.user.UserFilterDto;
import by.bntu.salaryapp.application.service.interfaces.user.UserService;
import by.bntu.salaryapp.domain.model.user.Role;
import by.bntu.salaryapp.domain.model.user.User;
import by.bntu.salaryapp.infrastructure.mapper.user.user.UserListMapper;
import by.bntu.salaryapp.infrastructure.mapper.user.user.UserMapper;
import by.bntu.salaryapp.infrastructure.persistence.repository.user.RoleRepository;
import by.bntu.salaryapp.infrastructure.persistence.repository.user.UserRepository;
import by.bntu.salaryapp.infrastructure.persistence.specifications.user.user.UserAccessibleBySpecification;
import by.bntu.salaryapp.infrastructure.persistence.specifications.user.user.UserFilterBySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
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

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final UserListMapper userListMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDtoOutput create(UserDtoInput dto) {
        log.info("Creating new user with username: {}", dto.getUsername());
        String email = dto.getEmail() != null ? dto.getEmail().trim() : null;
        String username = dto.getUsername() != null ? dto.getUsername().trim() : null;

        checkEmailUnique(email);
        checkUsernameUnique(username);

        User user = userMapper.toEntity(dto);
        user.setEmail(email);
        user.setUsername(username);

        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            log.warn("Password is required for user creation");
            throw new IllegalArgumentException("Password is required");
        }
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        UUID roleId = dto.getRoleId();

        if (roleId == null) {
            Role defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new EntityNotFoundException("Default role ROLE_USER not found"));
            assignSingleRoleToUser(user, defaultRole);
        } else {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));
            assignSingleRoleToUser(user, role);
        }

        User savedUser = userRepository.save(user);
        log.info("User created successfully with id: {} and username: {}", savedUser.getId(), savedUser.getUsername());
        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('SUPERUSER')")
    public UserDtoOutput update(UUID id, UserDtoInput dto) {
        log.info("Updating user with id: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        String newEmail = dto.getEmail() != null ? dto.getEmail().trim() : null;
        String newUsername = dto.getUsername() != null ? dto.getUsername().trim() : null;

        if (newEmail != null && !newEmail.equals(existingUser.getEmail())) {
            checkEmailUnique(newEmail);
            existingUser.setEmail(newEmail);
        }

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
            Role role = roleRepository.findById(dto.getRoleId())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found"));
            assignSingleRoleToUser(existingUser, role);
        }

        User updatedUser = userRepository.save(existingUser);
        log.info("User updated successfully with id: {}", updatedUser.getId());
        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        log.info("Deleting user with id: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        boolean isSuperUser = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_SUPERUSER"));

        if (!isSuperUser && !existingUser.getUsername().equals(currentUsername)) {
            log.warn("Access denied: user {} attempted to delete user {}", currentUsername, existingUser.getUsername());
            throw new AccessDeniedException("You can only delete your own profile");
        }
        userRepository.deleteById(id);
        log.info("User deleted successfully with id: {}", id);
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

    @Override
    public List<UserDtoOutput> getUsersByFilter(UserFilterDto filter) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (auth != null && auth.getPrincipal() instanceof User) {
            currentUser = (User) auth.getPrincipal();
        }

        Specification<User> accessSpec = new UserAccessibleBySpecification(currentUser);

        Specification<User> filterSpec = (filter != null) ? new UserFilterBySpecification(filter) : null;

        Specification<User> finalSpec = (filterSpec != null) ? accessSpec.and(filterSpec) : accessSpec;

        List<User> users = userRepository.findAll(finalSpec);
        return userListMapper.toDtoList(users);
    }

    @Override
    @Transactional
    public UserDtoOutput changePassword(UUID userId, String oldPassword, String newPassword) {
        log.info("Changing password for user with id: {}", userId);
        if (newPassword == null || newPassword.isBlank() || newPassword.length() < 8) {
            log.warn("Invalid password: too short");
            throw new IllegalArgumentException("New password must be at least 8 characters long");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            log.warn("Unauthenticated access attempt");
            throw new AccessDeniedException("Unauthenticated");
        }

        boolean isSuperUser = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_SUPERUSER"));

        String currentUsername = auth.getName();

        if (!isSuperUser) {
            if (!user.getUsername().equals(currentUsername)) {
                log.warn("Access denied: user {} attempted to change password for user {}", currentUsername, user.getUsername());
                throw new AccessDeniedException("You can only change your own password");
            }
            if (oldPassword == null || oldPassword.isBlank()) {
                log.warn("Old password is required");
                throw new IllegalArgumentException("Old password is required");
            }
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                log.warn("Incorrect old password for user: {}", user.getUsername());
                throw new AccessDeniedException("Old password is incorrect");
            }
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        User saved = userRepository.save(user);
        log.info("Password changed successfully for user: {}", saved.getUsername());
        return userMapper.toDto(saved);
    }


    private void assignSingleRoleToUser(User user, Role role) {
        user.setRole(role);
        log.debug("Role {} assigned to user: {}", role.getName(), user.getUsername());
    }

    private void checkEmailUnique(String email) {
        if (email != null && userRepository.existsByEmail(email)) {
            log.warn("Email already exists: {}", email);
            throw new IllegalArgumentException("User with email '" + email + "' already exists");
        }
    }

    private void checkUsernameUnique(String username) {
        if (username != null && userRepository.existsByUsername(username)) {
            log.warn("Username already exists: {}", username);
            throw new IllegalArgumentException("User with username '" + username + "' already exists");
        }
    }

    public UserDetailsService userDetailsService() {
        return username -> {
            log.debug("Loading user details for: {}", username);
            User user = userRepository.findByUsername(username)
                    .orElseGet(() -> {
                        log.debug("User not found by username, trying email: {}", username);
                        return userRepository.findByEmail(username)
                                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
                    });
            log.debug("User loaded: {} with role: {}", user.getUsername(), user.getRole() != null ? user.getRole().getName() : "NO_ROLE");
            return user;
        };
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).
                orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

    }

    public User getByUsername(String username) {
        log.debug("Fetching user by username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        log.debug("User fetched: {} with role: {}", user.getUsername(), user.getRole() != null ? user.getRole().getName() : "NO_ROLE");
        return user;
    }

    public UserDtoOutput getByEmailAsDto(String email) {
        log.debug("Fetching user by email: {}", email);
        User user = getByEmail(email);
        UserDtoOutput dto = userMapper.toDto(user);
        log.debug("User fetched: {} with role: {}", dto.getUsername(), dto.getRoleId());
        return dto;

    }
    public UserDtoOutput getByUsernameAsDto(String username) {
        log.debug("Fetching user by username: {}", username);
        User user = getByUsername(username);
        UserDtoOutput dto = userMapper.toDto(user);
        log.debug("User fetched: {} with role: {}", dto.getUsername(), dto.getRoleId());
        return dto;
    }
}
