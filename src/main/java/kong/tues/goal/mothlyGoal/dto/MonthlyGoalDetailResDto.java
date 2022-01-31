package kong.tues.goal.mothlyGoal.dto;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MonthlyGoalDetailResDto {

    private Long monthlyGoalId;
    private String name;
    private String content;
    private GoalType goalType;
    private Integer year;
    private Integer month;
    private AchieveType achieveType;
    private Integer goalCountQuota;
    private Integer goalCount;
    private Integer goalTimeQuota;
    private Integer goalTime;
    private Integer hours;
    private Integer minutes;
    private Boolean success;

    public static MonthlyGoalDetailResDto toDto(MonthlyGoal monthlyGoal) {
        return MonthlyGoalDetailResDto.builder()
                .monthlyGoalId(monthlyGoal.getId())
                .name(monthlyGoal.getName())
                .content(monthlyGoal.getContent())
                .goalType(monthlyGoal.getGoalType())
                .year(LocalDateTime.now().getYear())
                .month(LocalDateTime.now().getMonthValue())
                .achieveType(monthlyGoal.getAchieveType())
                .goalCountQuota(monthlyGoal.getGoalCountQuota())
                .goalCount(monthlyGoal.getGoalCount())
                .goalTimeQuota(monthlyGoal.getGoalTimeQuota())
                .goalTime(monthlyGoal.getGoalTime())
                .success(monthlyGoal.getSuccess())
                .hours(monthlyGoal.getWakeUpTime() != null ? monthlyGoal.getWakeUpTime().getHour() : null)
                .minutes(monthlyGoal.getWakeUpTime() != null ? monthlyGoal.getWakeUpTime().getMinute() : null)
                .build();
    }

}
