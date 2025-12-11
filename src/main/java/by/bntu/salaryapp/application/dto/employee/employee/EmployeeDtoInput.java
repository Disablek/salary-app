package by.bntu.salaryapp.application.dto.employee.employee;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDtoInput {
    private String fullName;

    private UUID position_id;

    private UUID subject_id;

    private UUID qualification_id;

    private UUID experience_id;
}
