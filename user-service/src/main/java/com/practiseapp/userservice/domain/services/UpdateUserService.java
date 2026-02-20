package com.practiseapp.userservice.domain.services;

import com.practiseapp.userservice.common.constants.UserConstant;
import com.practiseapp.userservice.common.customannotaions.UseCase;
import com.practiseapp.userservice.common.exception.GlobalException;
import com.practiseapp.userservice.common.exception.ResourceNotFoundException;
import com.practiseapp.userservice.domain.model.Role;
import com.practiseapp.userservice.domain.model.User;
import com.practiseapp.userservice.domain.ports.inbound.user.UpdateUserUseCase;
import com.practiseapp.userservice.domain.ports.outbound.role.RolePort;
import com.practiseapp.userservice.domain.ports.outbound.user.PasswordEncodePort;
import com.practiseapp.userservice.domain.ports.outbound.user.UserPort;
import lombok.AllArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@UseCase
@AllArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private UserPort userPort;
    private RolePort rolePort;
    private PasswordEncodePort passwordEncodePort;

    private ModelMapper modelMapper;

    @Override
    public void updateUser(String id ,User user) throws ResourceNotFoundException, GlobalException {
        if (user == null) {
            throw new ResourceNotFoundException(UserConstant.USER_IS_NULL);
        }
        User existingUser = userPort.findByEmail(id)
                .orElseThrow(() -> new ResourceNotFoundException(UserConstant.IS_NOT_PRESENT));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication != null ? authentication.getName() : null;

        boolean isSuperUser = false;
        if (authentication != null) {
            isSuperUser = authentication.getAuthorities().stream()
                    .anyMatch(a -> Objects.requireNonNull(a.getAuthority()).equals("ROLE_SUPERUSER"));
        }
        else{
            throw new GlobalException("Error updating user while getting authentication");
        }


        if (!isSuperUser && !existingUser.getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("You can only update your own profile");
        }

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncodePort.encode(user.getPassword()));
        }

        Role role = user.getRole();
        if (role != null) {
            if (!isSuperUser) {
                throw new AccessDeniedException("Only SUPERUSER can change roles");
            }
            else{
                existingUser.setRole(role);
            }
        }

        String newEmail = user.getEmail() != null ? user.getEmail().trim() : null;

        if (newEmail != null && !newEmail.isBlank()) {
            existingUser.setEmail(newEmail);
        }

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(user, existingUser);
        userPort.save(existingUser);

        //TODO: изменять JWT token

    }
}