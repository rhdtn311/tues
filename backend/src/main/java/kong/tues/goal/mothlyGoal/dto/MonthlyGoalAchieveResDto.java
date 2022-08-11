package kong.tues.goal.mothlyGoal.dto;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@AllArgsConstructor
@Data
public class MonthlyGoalAchieveResDto {

    private Long id;
    private AchieveType achieveType;
    private GoalType goalType;
    private Integer goalCountQuota;
    private Integer goalCount;
    private Integer goalTimeQuota;
    private Integer goalTime;
    private Boolean success;

    public static MonthlyGoalAchieveResDto entityToDto(MonthlyGoal monthlyGoal) {
        return new MonthlyGoalAchieveResDto(
                monthlyGoal.getId(),
                monthlyGoal.getAchieveType(),
                monthlyGoal.getGoalType(),
                monthlyGoal.getGoalCountQuota(),
                monthlyGoal.getGoalCount(),
                monthlyGoal.getGoalTimeQuota(),
                monthlyGoal.getGoalTime(),
                monthlyGoal.getSuccess()
        );
    }
}
