package by.bntu.salaryapp.datatable.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataTableCellDto {
    private UUID id;
    private UUID columnId;
    private String value;
}
