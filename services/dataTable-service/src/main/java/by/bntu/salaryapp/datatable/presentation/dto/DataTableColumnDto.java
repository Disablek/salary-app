package by.bntu.salaryapp.datatable.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataTableColumnDto {
    private UUID id;
    private String name;
    private String type;
    private Integer order;
    private String formula;
}
