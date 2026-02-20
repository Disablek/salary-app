package com.practiseapp.userservice.domain.ports.outbound.user;

import com.practiseapp.userservice.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPort extends JpaRepository<User,String> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    User getUserByEmail(String email);
}
