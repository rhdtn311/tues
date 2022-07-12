package kong.tues.goal.presentation.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateResponse {
    private final int year = LocalDate.now().getYear();
    private final int month = LocalDate.now().getMonthValue();
    private final int day = LocalDate.now().getDayOfMonth();
    private final int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
}
