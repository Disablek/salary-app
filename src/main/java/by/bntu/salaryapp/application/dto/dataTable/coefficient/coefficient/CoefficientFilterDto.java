package by.bntu.salaryapp.application.dto.dataTable.coefficient.coefficient;

import by.bntu.salaryapp.domain.common.enums.CoefficientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoefficientFilterDto {
    private UUID id;

    private String title;

    private String description;

    private Boolean isActive;

    private CoefficientType type;

    private Set<UUID> coefficientRulesIds = new HashSet<>();

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    private LocalDateTime createdAtFrom;

    private LocalDateTime createdAtTo;

    private LocalDateTime updatedAtFrom;

    private LocalDateTime updatedAtTo;
}
