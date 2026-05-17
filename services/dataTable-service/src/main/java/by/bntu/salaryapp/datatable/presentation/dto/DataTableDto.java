package by.bntu.salaryapp.datatable.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataTableDto {
    private UUID id;
    private String name;
    private String description;
    private List<DataTableColumnDto> columns = new ArrayList<>();
    private List<DataTableRowDto> rows = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
