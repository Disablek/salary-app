package by.bntu.salaryapp.user.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String surname;
    private String email;
    private String username;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}