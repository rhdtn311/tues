package kong.tues.goal.mothlyGoal.dto;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@AllArgsConstructor
@Data
public class MonthlyGoalAchieveReqDto {

    private Long monthlyGoalId;
    private Long dailyGoalId;
    private AchieveType achieveType;
    private GoalType goalType;

    private Integer value;
}
