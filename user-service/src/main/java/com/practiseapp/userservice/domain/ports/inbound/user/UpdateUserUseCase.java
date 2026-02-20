package com.practiseapp.userservice.domain.ports.inbound.user;

import com.practiseapp.userservice.common.exception.ResourceNotFoundException;
import com.practiseapp.userservice.domain.model.User;

public interface UpdateUserUseCase {
    void updateUser(String id ,User user) throws ResourceNotFoundException;
}
