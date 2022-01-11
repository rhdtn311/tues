package kong.tues.goal.mothlyGoal.dto;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.member.domain.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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

    private String content;

    @Nullable
    private GoalType goalType;

    @Nullable
    private AchieveType achieveType;

    @Nullable
    @Max(100000000)
    private Integer goalCountQuota;

    @Nullable
    @Max(value = 100000000)
    private Integer goalCount;

    @Nullable
    @Max(1000)
    private Integer goalTimeQuota;

    @Nullable
    @Max(1000)
    private Integer goalTime;

    private Integer year;

    private Integer month;

    private Boolean success;

    public MonthlyGoal toMonthlyGoal(Member member) {
        return MonthlyGoal.builder()
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
    }
}
