package by.bntu.salaryapp.application.dto.user.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoInput {
    private UUID id;

    private String firstName;

    private String lastName;

    private String surName;

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 8, message = "Password must include at least 8 symbols")
    @Size(max = 255, message = "The password length must be no more than 255 characters")
    private String password;

    private String username;

    private Set<UUID> rolesId = new HashSet<>();

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}