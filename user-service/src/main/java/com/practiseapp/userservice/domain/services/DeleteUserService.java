package com.practiseapp.userservice.domain.services;

import com.practiseapp.userservice.common.constants.UserConstant;
import com.practiseapp.userservice.common.customannotaions.UseCase;
import com.practiseapp.userservice.common.exception.GlobalException;
import com.practiseapp.userservice.common.exception.ResourceNotFoundException;
import com.practiseapp.userservice.domain.model.User;
import com.practiseapp.userservice.domain.ports.inbound.user.DeleteUserUseCase;
import com.practiseapp.userservice.domain.ports.outbound.user.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@UseCase
public class DeleteUserService implements DeleteUserUseCase {

    private UserPort userPort;

    @Override
    public void deleteUserById(String id) throws ResourceNotFoundException, GlobalException {
        if (id == null || id.trim().isEmpty()) {
            throw new GlobalException(UserConstant.REQUIRED_PARAMETER + "id");
        }
        User user = userPort.findByEmail(id.trim())
                .orElseThrow(() -> new ResourceNotFoundException(UserConstant.USER_NOT_FOUND));

        user.setIsActive(false);

        userPort.save(user);
    }
}
