package by.bntu.salaryapp.application.dto.employee.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFilterDto {
    private String firstName;

    private String lastName;

    private String surName;

    private UUID position_id;

    private Set<UUID> subject_id;

    private UUID qualification_id;

    private UUID experience_id;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    private LocalDateTime createdAtFrom;

    private LocalDateTime updatedAtFrom;

    private LocalDateTime createdAtTo;

    private LocalDateTime updatedAtTo;
}
