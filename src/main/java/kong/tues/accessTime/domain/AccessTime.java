package kong.tues.accessTime.domain;

import kong.tues.member.domain.Member;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalTime;

@Table(name = "accessTime")
@Entity
public class AccessTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    private LocalTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
