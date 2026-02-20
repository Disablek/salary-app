package com.practiseapp.userservice.infrastructure.security;

import com.practiseapp.userservice.domain.ports.outbound.user.PasswordEncodePort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncodeAdapter implements PasswordEncodePort {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
