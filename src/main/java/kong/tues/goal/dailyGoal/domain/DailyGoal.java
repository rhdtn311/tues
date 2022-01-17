package kong.tues.goal.dailyGoal.domain;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.member.domain.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.*;
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

    @Nullable
    @Column(name = "goalCountQuota")
    private Integer goalCountQuota;

    @Nullable
    @Column(name = "goalCount")
    private Integer goalCount;

    @Nullable
    @Column(name = "goalTimeQuota")
    private Integer goalTimeQuota;

    @Nullable
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

}
