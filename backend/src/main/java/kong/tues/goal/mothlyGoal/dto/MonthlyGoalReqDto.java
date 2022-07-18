package kong.tues.goal.mothlyGoal.dto;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.member.domain.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MonthlyGoalReqDto {

    @Nullable
    private Long monthlyGoalId;

    @NotBlank(message = "목표의 제목을 입력해주세요.")
    @Length(max = 50)
    private String name;

    @Nullable
    private String content;

    @NotNull(message = "목표의 타입을 설정해주세요.")
    private GoalType goalType;
    @NotNull(message = "목표의 종류를 입력해주세요.")
    private AchieveType achieveType;

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

    @Range(min = 0, max=3000, message="0년에서 3000년 사이여야 합니다.")
    private Integer year;

    @Range(min = 1, max = 12, message="1월에서 12월 사이여야 합니다.")
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

        return monthlyGoal;
    }
}
