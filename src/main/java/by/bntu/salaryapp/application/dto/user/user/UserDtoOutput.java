package by.bntu.salaryapp.application.dto.user.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoOutput {
    private UUID id;

    @NotNull
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @NotNull
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    private String surName;

    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotNull
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    private UUID roleId;

    private String roleName;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
