package kong.tues.goal.dailyGoal.domain;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalReqDto;
import kong.tues.goal.exception.GoalCountOutOfRangeException;
import kong.tues.goal.exception.GoalTimeOutOfRangeException;
import kong.tues.member.domain.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "daily_goal")
public class DailyGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotEmpty
    @Length(max = 50)
    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "goalType")
    private GoalType goalType;

    @NotNull
    @Column(name = "achieveType")
    private AchieveType achieveType;

    @Nullable
    @Column(name = "wakeUpTime")
    private LocalTime wakeUpTime;

    @Max(value = 100000000)
    @Nullable
    @Column(name = "goalCountQuota")
    private Integer goalCountQuota;

    @Max(value = 100000000)
    @Nullable
    @Column(name = "goalCount")
    private Integer goalCount;

    @Nullable @Min(0) @Max(24)
    @Column(name = "goalTimeQuota")
    private Integer goalTimeQuota;

    @Nullable @Min(0) @Max(24)
    @Column(name = "goalTime")
    private Integer goalTime;

    @NotNull
    @Column(name = "date")
    private LocalDate date;

    @NotNull
    @Column(name = "success")
    private Boolean success;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_eid")
    private Member member;

    public void plusGoalCount() {
        if (this.goalCount >= 100000000) {
            throw new GoalCountOutOfRangeException();
        }

        goalCount++;
        checkSuccess();
    }

    public void plusGoalTime() {
        if (goalTime >= 24) {
            throw new GoalTimeOutOfRangeException();
        }
        goalTime++;
        checkSuccess();
    }

    public void minusGoalCount() {
        if (goalCount <= 0) {
            throw new GoalCountOutOfRangeException();
        }

        goalCount--;
        checkSuccess();
    }

    public void minusGoalTime() {
        if (goalTime <= 0) {
            throw new GoalTimeOutOfRangeException();
        }

        goalTime--;
        checkSuccess();
    }

    public void checkSuccess() {
        switch (achieveType) {
            case COUNT : success = goalCountQuota <= goalCount; break;
            case TIME : success = goalTimeQuota <= goalTime; break;
            case WAKE : success = wakeUpTime.isAfter(LocalTime.now()); break;
            case BASIC : success = !success;
        }
    }

    public DailyGoal update(DailyGoalReqDto dailyGoalReqDto) {
        this.setName(dailyGoalReqDto.getName());
        this.setContent(dailyGoalReqDto.getContent());
        this.setGoalType(dailyGoalReqDto.getGoalType());
        this.setAchieveType(dailyGoalReqDto.getAchieveType());
        this.setGoalCountQuota(dailyGoalReqDto.getGoalCountQuota());
        this.setGoalTimeQuota(dailyGoalReqDto.getGoalTimeQuota());

        if (dailyGoalReqDto.getWakeUpHours() != null) {
            this.setWakeUpTime(LocalTime.of(dailyGoalReqDto.getWakeUpHours(), dailyGoalReqDto.getWakeUpMinutes()));
        }

        if (dailyGoalReqDto.getAchieveType() == AchieveType.BASIC) {
            this.success = false;
        } else if (dailyGoalReqDto.getAchieveType() == AchieveType.TIME) {
            this.success = this.goalTime >= this.goalTimeQuota;
        } else if (dailyGoalReqDto.getAchieveType() == AchieveType.COUNT) {
            this.success = this.goalCount >= this.goalCountQuota;
        } else if (dailyGoalReqDto.getAchieveType() == AchieveType.WAKE) {
            this.success = false;
        }

        return this;
    }

}
