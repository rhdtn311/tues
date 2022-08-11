package kong.tues.goal.dailyGoal.application.dto;

import kong.tues.goal.mothlyGoal.dto.MonthlyGoalMainResDto;
import lombok.Data;

import java.util.List;

@Data
public class GoalAchieveResDto {

    private List<MonthlyGoalMainResDto> monthlyGoals;
    private List<DailyGoalMainResDto> dailyGoals;

}
