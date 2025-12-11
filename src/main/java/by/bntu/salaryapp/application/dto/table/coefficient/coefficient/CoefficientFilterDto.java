package by.bntu.salaryapp.application.dto.table.coefficient.coefficient;

import by.bntu.salaryapp.domain.common.enums.CoefficientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Set<UUID> rulesId = new HashSet<>();
}
