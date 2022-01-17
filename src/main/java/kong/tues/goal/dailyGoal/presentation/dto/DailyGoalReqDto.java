package kong.tues.goal.dailyGoal.presentation.dto;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyGoalReqDto {

    @NotEmpty
    @Length(max = 50)
    private String name;

    @Nullable
    private String content;

    @NotNull
    private GoalType goalType;

    @NotNull
    private AchieveType achieveType;

    @Min(0) @Max(23)
    @Nullable
    private Integer wakeUpHours;

    @Min(0) @Max(59)
    @Nullable
    private Integer wakeUpMinutes;

    @Nullable
    @Min(0) @Max(value = 100000000)
    private Integer goalCountQuota;

    @Nullable
    @Min(0) @Max(value = 100000000)
    private Integer goalCount;

    @Nullable
    @Min(0) @Max(24)
    private Integer goalTimeQuota;

    @Nullable
    @Min(0) @Max(24)
    private Integer goalTime;

    private Integer year;

    private Integer month;

    private Integer day;

    private Boolean success;

    public DailyGoal toDailyGoal(Member member) {
        DailyGoal dailyGoal = DailyGoal.builder()
                .member(member)
                .name(name)
                .content(content)
                .goalType(goalType)
                .achieveType(achieveType)
                .goalCountQuota(goalCountQuota)
                .goalCount(0)
                .goalTimeQuota(goalTimeQuota)
                .goalTime(0)
                .date(LocalDate.of(year, month, day))
                .success(false)
                .build();

        if (this.getAchieveType() == AchieveType.WAKE) {
            dailyGoal.setWakeUpTime(LocalTime.of(wakeUpHours, wakeUpMinutes));
        }

        return dailyGoal;
    }


}
