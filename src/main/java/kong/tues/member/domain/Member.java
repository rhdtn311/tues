package kong.tues.member.domain;

import kong.tues.achievement.domain.Achievement;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.member.domain.repository.MemberRepository;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @Column(columnDefinition = "TINYTEXT")
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

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
