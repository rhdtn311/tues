package kong.tues.member.domain;

import kong.tues.achievement.domain.Achievement;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long id;

    @NotNull @NotEmpty
    @Length(max = 15)
    String loginId;

    @NotNull @NotEmpty
    @Length(max = 15)
    String password;

    @NotNull @NotEmpty
    @Email
    String mail;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Achievement> achievement;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<MonthlyGoal> monthlyGoal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<DailyGoal> dailyGoal;
}
