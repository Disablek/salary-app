package by.bntu.salaryapp.application.dto.employee.employee;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private UUID id;

    private String fullName;

    private String firstName;

    private String lastName;

    private String patronymic;

    private String email;

    private String phoneNumber;

    private LocalDate hireDate;

    private Integer yearsOfExperience;

    private UUID position_id;

    private String positionName;

    private Set<UUID> subjects_id;

    private UUID qualification_id;

    private String qualificationName;

    private UUID experience_id;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
