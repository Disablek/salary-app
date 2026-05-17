package by.bntu.salaryapp.datatable.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataTableRowDto {
    private UUID id;
    private UUID employeeId;
    private Integer order;
    private List<DataTableCellDto> cells = new ArrayList<>();
}
