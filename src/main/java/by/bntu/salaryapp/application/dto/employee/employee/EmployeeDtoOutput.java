package by.bntu.salaryapp.application.dto.employee.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDtoOutput {
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

    @CreatedDate
    private LocalDateTime createdAtFrom;
    @CreatedDate
    private LocalDateTime createdAtTo;

    @LastModifiedDate
    private LocalDateTime updatedAtFrom;
    @LastModifiedDate
    private LocalDateTime updatedAtTo;
}
