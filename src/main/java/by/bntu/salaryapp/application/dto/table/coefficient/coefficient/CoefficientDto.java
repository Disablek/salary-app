package by.bntu.salaryapp.application.dto.table.coefficient.coefficient;

import by.bntu.salaryapp.domain.common.enums.CoefficientType;
import by.bntu.salaryapp.domain.model.table.coefficient.CoefficientRule;
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
public class CoefficientDto {
    private UUID id;

    private String title;

    private String description;

    private Boolean isActive;

    private CoefficientType type;

    private Set<UUID> rulesId = new HashSet<>();
}
