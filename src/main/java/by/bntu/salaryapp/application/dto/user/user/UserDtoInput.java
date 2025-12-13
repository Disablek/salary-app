package by.bntu.salaryapp.application.dto.user.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoInput {
    private UUID id;

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    private String surName;

    @NotEmpty
    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 8, message = "Password must include at least 8 symbols")
    @Size(max = 255, message = "The password length must be no more than 255 characters")
    private String password;

    @NotEmpty(message = "UserName cannot be empty")
    private String username;

    private UUID roleId;
}