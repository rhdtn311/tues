package kong.tues.goal.dailyGoal.application.dto;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalUpdateResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DailyGoalUpdateResDto {
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

    public static DailyGoalUpdateResDto toDto(DailyGoal dailyGoal) {
        return DailyGoalUpdateResDto.builder()
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
                .goalTimeQuota(dailyGoal.getGoalTime())
                .hours(dailyGoal.getWakeUpTime() != null ? dailyGoal.getWakeUpTime().getHour() : null)
                .minutes(dailyGoal.getWakeUpTime() != null ? dailyGoal.getWakeUpTime().getMinute() : null)
                .build();
    }

}
