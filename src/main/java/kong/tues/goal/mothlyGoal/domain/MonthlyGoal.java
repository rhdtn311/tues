package kong.tues.goal.mothlyGoal.domain;

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

    @Column(name = "goalCountQuota")
    private Integer goalCountQuota;

    @Column(name = "goalCount")
    private Integer goalCount;

    @Column(name = "goalTimeQuota")
    private Integer goalTimeQuota;

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
}
