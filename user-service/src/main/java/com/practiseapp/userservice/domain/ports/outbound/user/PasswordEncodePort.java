package com.practiseapp.userservice.domain.ports.outbound.user;

public interface PasswordEncodePort {
    String encode(CharSequence password);
}
