package kong.tues.goal.dailyGoal.application.dto;

import kong.tues.goal.GoalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatedMonthlyGoalResDto {

    private GoalType goalType;
    private String name;
}
