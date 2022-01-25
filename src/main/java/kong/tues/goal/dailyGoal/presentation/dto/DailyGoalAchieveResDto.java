package kong.tues.goal.dailyGoal.presentation.dto;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalAchieveResDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@AllArgsConstructor
@Data
public class DailyGoalAchieveResDto {

    private Long id;
    private AchieveType achieveType;
    private GoalType goalType;
    private Integer goalCountQuota;
    private Integer goalCount;
    private Integer goalTimeQuota;
    private Integer goalTime;
    private LocalTime wakeUpTime;
    private Boolean success;

    public static DailyGoalAchieveResDto entityToDto(DailyGoal dailyGoal) {
        return new DailyGoalAchieveResDto(
                dailyGoal.getId(),
                dailyGoal.getAchieveType(),
                dailyGoal.getGoalType(),
                dailyGoal.getGoalCountQuota(),
                dailyGoal.getGoalCount(),
                dailyGoal.getGoalTimeQuota(),
                dailyGoal.getGoalTime(),
                dailyGoal.getWakeUpTime(),
                dailyGoal.getSuccess()
        );
    }}
