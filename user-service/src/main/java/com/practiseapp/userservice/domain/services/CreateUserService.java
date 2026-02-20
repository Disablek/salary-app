package com.practiseapp.userservice.domain.services;

import com.practiseapp.userservice.common.constants.RoleConstant;
import com.practiseapp.userservice.common.constants.UserConstant;
import com.practiseapp.userservice.common.customannotaions.UseCase;
import com.practiseapp.userservice.common.exception.GlobalException;
import com.practiseapp.userservice.common.exception.ParameterNotFoundException;
import com.practiseapp.userservice.domain.model.Role;
import com.practiseapp.userservice.domain.model.User;
import com.practiseapp.userservice.domain.ports.inbound.user.CreateUserUseCase;
import com.practiseapp.userservice.domain.ports.outbound.role.RolePort;
import com.practiseapp.userservice.domain.ports.outbound.user.PasswordEncodePort;
import com.practiseapp.userservice.domain.ports.outbound.user.UserPort;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@UseCase
public class CreateUserService implements CreateUserUseCase {

    private UserPort userPort;
    private RolePort rolePort;
    private PasswordEncodePort passwordEncodePort;

    @Override
    public void createUser(User user) throws GlobalException, ParameterNotFoundException {
        if (user == null) {
            throw new GlobalException(UserConstant.USER_IS_NULL);
        }

        if (StringUtils.isBlank(user.getFistName())) {
            getMessageParameterNotFoundException("firstName");
        }
        if (StringUtils.isBlank(user.getLastName())) {
            getMessageParameterNotFoundException("lastName");
        }
        if (user.getSurName() != null && user.getSurName().trim().isEmpty()) {
            getMessageParameterNotFoundException("surName");
        }
        if (StringUtils.isBlank(user.getEmail())) {
            getMessageParameterNotFoundException("email");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            getMessageParameterNotFoundException("password");
        }

        if (userPort.existsByEmail(user.getEmail())) {
            throw new GlobalException(UserConstant.THIS_EMAIL_IS_ALREADY_REGISTERED);
        }

        Optional<Role> role = rolePort.getFirstRow();

        if (role.isEmpty()) {
            throw new GlobalException(RoleConstant.ROLES_DOESNT_EXISTS);
        }

        user.setRole(role.get());
        user.setPassword(passwordEncodePort.encode(user.getPassword()));

        userPort.save(user);
    }

    private void getMessageParameterNotFoundException(String parameter) throws ParameterNotFoundException {
        throw new ParameterNotFoundException(UserConstant.REQUIRED_PARAMETER + "\"" + parameter + "\"" + UserConstant.IS_NOT_PRESENT);
    }
}
