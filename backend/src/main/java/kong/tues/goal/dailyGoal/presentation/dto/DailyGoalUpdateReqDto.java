package kong.tues.goal.dailyGoal.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyGoalUpdateReqDto {
    private Long dailyGoalId;
    private Integer year;
    private Integer month;
    private Integer day;
}
