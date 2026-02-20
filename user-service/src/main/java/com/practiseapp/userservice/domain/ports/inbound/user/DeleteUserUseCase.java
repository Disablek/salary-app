package com.practiseapp.userservice.domain.ports.inbound.user;

import com.practiseapp.userservice.common.exception.GlobalException;
import com.practiseapp.userservice.common.exception.ResourceNotFoundException;
import com.practiseapp.userservice.domain.model.User;

public interface DeleteUserUseCase {
    void deleteUserById(String id) throws ResourceNotFoundException, GlobalException;
}
