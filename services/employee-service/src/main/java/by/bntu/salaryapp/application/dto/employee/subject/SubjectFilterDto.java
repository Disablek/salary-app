package by.bntu.salaryapp.application.dto.employee.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectFilterDto {
    private UUID id;

    private String title;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    private LocalDateTime createdAtFrom;

    private LocalDateTime updatedAtFrom;

    private LocalDateTime createdAtTo;

    private LocalDateTime updatedAtTo;
}
