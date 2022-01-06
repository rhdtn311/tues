package kong.tues.goal.dailyGoal.domain;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.member.domain.Member;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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

    @NotNull @NotEmpty
    @Column(name = "goalType")
    private GoalType goalType;

    @NotNull @NotEmpty
    @Column(name = "achieveType")
    private AchieveType achieveType;

    @Column(name = "goalCountQuota")
    private int goalCountQuota;

    @Column(name = "goalCount")
    private int goalCount;

    @Column(name = "goalTimeQuota")
    private int goalTimeQuota;

    @Column(name = "goalTime")
    private int goalTime;

    @NotNull @NotEmpty
    @Column(name = "date")
    private LocalDate date;

    @NotNull @NotEmpty
    @Column(name = "success")
    private Boolean success;

    @NotNull @NotEmpty
    @Column(name = "active")
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_eid")
    private Member member;
}