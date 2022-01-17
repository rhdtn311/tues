package kong.tues.goal.mothlyGoal.dto;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.member.domain.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MonthlyGoalReqDto {

    @NotEmpty
    @NotNull
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
    @Min(0) @Max(100000000)
    private Integer goalCount;

    @Nullable
    @Min(0) @Max(1000)
    private Integer goalTimeQuota;

    @Nullable
    @Min(0) @Max(1000)
    private Integer goalTime;

    private Integer year;

    private Integer month;

    private Boolean success;

    public MonthlyGoal toMonthlyGoal(Member member) {
        MonthlyGoal monthlyGoal = MonthlyGoal.builder()
                .member(member)
                .name(name)
                .content(content)
                .goalType(goalType)
                .achieveType(achieveType)
                .goalCountQuota(goalCountQuota)
                .goalCount(0)
                .goalTimeQuota(goalTimeQuota)
                .goalTime(0)
                .date(LocalDate.of(year, month, 1))
                .success(false)
                .build();

        if (this.getAchieveType() == AchieveType.WAKE) {
            monthlyGoal.setWakeUpTime(LocalTime.of(wakeUpHours, wakeUpMinutes));
        }

        return monthlyGoal;
    }
}
