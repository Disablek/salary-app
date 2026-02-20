package com.practiseapp.userservice.domain.ports.outbound.role;

import com.practiseapp.userservice.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RolePort extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r ORDER BY r.id")
    Optional<Role> getFirstRow();
}
