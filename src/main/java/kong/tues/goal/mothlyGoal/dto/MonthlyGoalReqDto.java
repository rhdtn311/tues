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

    @Nullable
    private Long monthlyGoalId;

    @NotEmpty(message = "값을 입력해주세요.")
    @NotNull(message = "값을 입력해주세요.")
    @Length(max = 50)
    private String name;

    @Nullable
    private String content;

    @NotNull(message = "값을 입력해주세요.")
    private GoalType goalType;

    @NotNull(message = "값을 입력해주세요.")
    private AchieveType achieveType;

    @Min(value = 0, message = "0이상") @Max(value = 23, message = "23미만")
    @Nullable
    private Integer wakeUpHours;

    @Min(value = 0, message = "0이상") @Max(value = 59, message = "59미만")
    @Nullable
    private Integer wakeUpMinutes;

    @Nullable
    @Min(value = 0, message = "0이상") @Max(value = 100000000, message = "1000000미만")
    private Integer goalCountQuota;

    @Nullable
    @Min(value = 0, message = "0이상") @Max(value = 100000000, message = "100000미만")
    private Integer goalCount;

    @Nullable
    @Min(value = 0, message = "0이상") @Max(value = 1000, message = "1000미만")
    private Integer goalTimeQuota;

    @Nullable
    @Min(value = 0, message = "0이상") @Max(value = 1000,message = "1000미만")
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
