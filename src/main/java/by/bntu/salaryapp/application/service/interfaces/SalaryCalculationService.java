package by.bntu.salaryapp.application.service.interfaces;


import java.util.UUID;

public interface SalaryCalculationService {
    /**
     * Пересчитывает все зависимые ячейки в конкретной строке.
     */
    void calculateRow(UUID rowId);

    /**
     * Пересчитывает всю таблицу целиком (все строки).
     */
    void calculateTable(UUID tableId);
}