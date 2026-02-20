package com.practiseapp.userservice.domain.ports.outbound.user;

public interface PasswordMatchesPort {
    boolean matches(CharSequence rawPassword, String encodedPassword);}
