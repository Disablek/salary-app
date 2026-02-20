package com.practiseapp.userservice.domain.ports.inbound.user;

import com.practiseapp.userservice.common.exception.ParameterNotFoundException;
import com.practiseapp.userservice.domain.model.User;

public interface CreateUserUseCase {
    void createUser(User user) throws Exception, ParameterNotFoundException;
}
