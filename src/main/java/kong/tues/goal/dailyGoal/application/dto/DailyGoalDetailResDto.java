package kong.tues.goal.dailyGoal.application.dto;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalDetailResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DailyGoalDetailResDto {

    private Long dailyGoalId;
    private String name;
    private String content;
    private GoalType goalType;
    private Integer year;
    private Integer month;
    private Integer day;
    private AchieveType achieveType;
    private Integer goalCountQuota;
    private Integer goalCount;
    private Integer goalTimeQuota;
    private Integer goalTime;
    private Integer hours;
    private Integer minutes;
    private Boolean success;

    public static DailyGoalDetailResDto toDto(DailyGoal dailyGoal) {
        return DailyGoalDetailResDto.builder()
                .dailyGoalId(dailyGoal.getId())
                .name(dailyGoal.getName())
                .content(dailyGoal.getContent())
                .goalType(dailyGoal.getGoalType())
                .year(dailyGoal.getDate().getYear())
                .month(dailyGoal.getDate().getMonthValue())
                .day(dailyGoal.getDate().getDayOfMonth())
                .achieveType(dailyGoal.getAchieveType())
                .goalCountQuota(dailyGoal.getGoalCountQuota())
                .goalCount(dailyGoal.getGoalCount())
                .goalTimeQuota(dailyGoal.getGoalTimeQuota())
                .goalTime(dailyGoal.getGoalTime())
                .success(dailyGoal.getSuccess())
                .hours(dailyGoal.getWakeUpTime() != null ? dailyGoal.getWakeUpTime().getHour() : null)
                .minutes(dailyGoal.getWakeUpTime() != null ? dailyGoal.getWakeUpTime().getMinute() : null)
                .build();
    }
}
