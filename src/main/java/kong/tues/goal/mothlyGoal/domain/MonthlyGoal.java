package kong.tues.goal.mothlyGoal.domain;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.exception.GoalCountOutOfRangeException;
import kong.tues.goal.exception.GoalTimeOutOfRangeException;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalReqDto;
import kong.tues.member.domain.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "monthly_goal")
public class MonthlyGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull @NotEmpty
    @Length(max = 50)
    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Nullable
    @Column(name = "goalType")
    private GoalType goalType;

    @Nullable
    @Column(name = "achieveType")
    private AchieveType achieveType;

    @Column(name = "wakeUpTime")
    private LocalTime wakeUpTime;

    @Min(0) @Max(100000000)
    @Column(name = "goalCountQuota")
    private Integer goalCountQuota;

    @Min(0) @Max(100000000)
    @Column(name = "goalCount")
    private Integer goalCount;

    @Min(0) @Max(1000)
    @Column(name = "goalTimeQuota")
    private Integer goalTimeQuota;

    @Min(0) @Max(1000)
    @Column(name = "goalTime")
    private Integer goalTime;

    @NotNull
    @Column(name = "date")
    private LocalDate date;

    @NotNull
    @Column(name = "success")
    private Boolean success;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void plusGoalCount() {
        if (goalCount >= 100000000) {
            throw new GoalCountOutOfRangeException();
        }
        this.goalCount++;
        checkSuccess();
    }

    public void plusGoalTime() {
        if (goalTime >= 1000) {
            throw new GoalTimeOutOfRangeException();
        }
        this.goalTime++;
        checkSuccess();
    }

    public void minusGoalCount() {
        if (goalCount <= 0) {
            throw new GoalCountOutOfRangeException();
        }
        this.goalCount--;
        checkSuccess();
    }

    public void minusGoalTime() {
        if (goalTime <= 0) {
            throw new GoalTimeOutOfRangeException();
        }
        this.goalTime--;
        checkSuccess();
    }

    public void checkSuccess() {
        switch (achieveType) {
            case COUNT : success = goalCountQuota <= goalCount; break;
            case TIME : success = goalTimeQuota <= goalTime; break;
            case BASIC : success = !success;
        }
    }

    public void minusGoalCounts(Integer minusGoalCounts) {
        goalCount -= minusGoalCounts;

        if (goalCount < 0) {
            goalCount = 0;
        }
    }

    public void minusGoalTimes(Integer minusGoalTimes) {
        goalTime -= minusGoalTimes;

        if (goalTime < 0) {
            goalTime = 0;
        }
    }

    public MonthlyGoal update(MonthlyGoalReqDto monthlyGoalReqDto) {
        this.setName(monthlyGoalReqDto.getName());
        this.setContent(monthlyGoalReqDto.getContent());
        this.setGoalType(monthlyGoalReqDto.getGoalType());
        this.setAchieveType(monthlyGoalReqDto.getAchieveType());
        this.setGoalCountQuota(monthlyGoalReqDto.getGoalCountQuota());
        this.setGoalTimeQuota(monthlyGoalReqDto.getGoalTimeQuota());

        if (monthlyGoalReqDto.getWakeUpHours() != null) {
            this.setWakeUpTime(LocalTime.of(monthlyGoalReqDto.getWakeUpHours(), monthlyGoalReqDto.getWakeUpMinutes()));
        }

        if (monthlyGoalReqDto.getAchieveType() == AchieveType.BASIC) {
            this.success = false;
        } else if (monthlyGoalReqDto.getAchieveType() == AchieveType.TIME) {
            this.success = this.goalTime >= this.goalTimeQuota;
        } else if (monthlyGoalReqDto.getAchieveType() == AchieveType.COUNT) {
            this.success = this.goalCount >= this.goalCountQuota;
        } else if (monthlyGoalReqDto.getAchieveType() == AchieveType.WAKE) {
            this.success = false;
        }

        return this;
    }
}
