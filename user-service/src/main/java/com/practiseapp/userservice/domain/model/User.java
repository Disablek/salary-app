package com.practiseapp.userservice.domain.model;

import com.practiseapp.userservice.common.exception.CustomExceptionHandler;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.integration.annotation.Default;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collection;
import java.util.Collections;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "users")
public class User extends BaseAuditingEntity implements UserDetails {
    @Id
    private String email;

    @NotEmpty
    private String fistName;

    @NotEmpty
    private String lastName;

    private String surName;

    @NotEmpty
    private String password;

    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @NotNull
    private Boolean isActive = true;

    @PrePersist
    public void prePersist() {
        if (isActive == null) {
            isActive = true;  // Финальная защита
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role != null ?
                Collections.singletonList(role) :
                Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    @Override
    public String toString() {
        return "User{" +
                ", email='" + email + '\'' +
                "username='" + username + '\'' +
                "firstName='" + fistName + '\'' +
                "lastName='" + lastName + '\'' +
                ", lastName='" + surName + '\'' +
                '}';
    }
}
