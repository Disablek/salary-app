package com.practiseapp.userservice.domain.ports.inbound.user;

import com.practiseapp.userservice.common.exception.GlobalException;
import com.practiseapp.userservice.common.exception.ParameterNotFoundException;
import com.practiseapp.userservice.common.exception.ResourceNotFoundException;
import com.practiseapp.userservice.domain.model.User;

public interface UserLoginUseCase {
    User userLogin(String email, String password) throws ResourceNotFoundException, GlobalException, ParameterNotFoundException;
}
