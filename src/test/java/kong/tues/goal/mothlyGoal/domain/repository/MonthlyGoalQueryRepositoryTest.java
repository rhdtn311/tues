package kong.tues.goal.mothlyGoal.domain.repository;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalReqDto;
import kong.tues.member.domain.Member;
import kong.tues.member.domain.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MonthlyGoalQueryRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MonthlyGoalRepository monthlyGoalRepository;

    @Autowired
    MonthlyGoalQueryRepository monthlyGoalQueryRepository;

    static Member member1;
    static Member member2;
    static MonthlyGoal monthlyGoal1;
    static MonthlyGoal monthlyGoal2;
    static MonthlyGoal monthlyGoal3;
    static MonthlyGoal monthlyGoal4;

    @BeforeEach
    void init() {
        member1 = Member.builder()
                .loginId("rhdtn311")
                .password("xogus123")
                .mail("rhdtn311@naver.com")
                .build();

        member2 = Member.builder()
                .loginId("kimkim123")
                .password("kim123")
                .mail("kimkim123@naver.com")
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        monthlyGoal1 = MonthlyGoal.builder()
                .member(member1)
                .name("책 10권 이상 읽기")
                .content("매일 책을 읽은 습관을 기르기 위해 하루도 빠짐 없이 독서를 한다.")
                .goalType(GoalType.A)
                .achieveType(AchieveType.COUNT)
                .goalCountQuota(10)
                .goalCount(0)
                .goalTimeQuota(null)
                .goalTime(0)
                .date(LocalDate.of(2022,1,14))
                .success(false)
                .build();

        monthlyGoal2 = MonthlyGoal.builder()
                .member(member1)
                .name("인터넷 강의 100시간 듣기")
                .content("구입해놨던 강의를 모두 듣는다.")
                .goalType(GoalType.B)
                .achieveType(AchieveType.COUNT)
                .goalCountQuota(null)
                .goalCount(0)
                .goalTimeQuota(100)
                .goalTime(0)
                .date(LocalDate.of(2022,2,14))
                .success(false)
                .build();

        monthlyGoal3 = MonthlyGoal.builder()
                .member(member1)
                .name("매일 3시간씩 청소하기")
                .content("깔끔한 습관을 기르기 위해 매일 청소를 한다.")
                .goalType(GoalType.C)
                .achieveType(AchieveType.TIME)
                .goalCountQuota(null)
                .goalCount(0)
                .goalTimeQuota(100)
                .goalTime(0)
                .date(LocalDate.of(2022,2,14))
                .success(false)
                .build();

        monthlyGoal4 = MonthlyGoal.builder()
                .member(member2)
                .name("매일 3시간씩 청소하기")
                .content("깔끔한 습관을 기르기 위해 매일 청소를 한다.")
                .goalType(GoalType.A)
                .achieveType(AchieveType.TIME)
                .goalCountQuota(null)
                .goalCount(0)
                .goalTimeQuota(100)
                .goalTime(0)
                .date(LocalDate.of(2022,1,14))
                .success(false)
                .build();

        monthlyGoalRepository.save(monthlyGoal1);
        monthlyGoalRepository.save(monthlyGoal2);
        monthlyGoalRepository.save(monthlyGoal3);
        monthlyGoalRepository.save(monthlyGoal4);

    }

    @Test
    @DisplayName("특정 회원의 사용중인 목표 가져오기")
    public void findCreatedMonthlyGoal() {

        // when
        int year = 2022;
        int month = 1;
        List<GoalType> createdGoalTypes = monthlyGoalQueryRepository.findCreatedGoalTypes(member1.getId(), year, month);

        // then
        assertThat(createdGoalTypes.size()).isEqualTo(1);
        assertThat(createdGoalTypes.contains(GoalType.A)).isTrue();

    }

    @Test
    @DisplayName("목표의 시행 월과 현재 월이 다른 경우")
    public void differentMonth() {

        // given
        int year = 2022;
        int month = 2;

        // when
        List<GoalType> createdGoalTypes = monthlyGoalQueryRepository.findCreatedGoalTypes(member1.getId(), year, month);

        // then
        assertThat(createdGoalTypes.size()).isEqualTo(2);
        assertThat(createdGoalTypes.get(0)).isEqualTo(GoalType.B);
        assertThat(createdGoalTypes.get(1)).isEqualTo(GoalType.C);

    }

    @Test
    @DisplayName("목표의 시행 월은 같지만 년도가 다른 경우")
    public void sameMonthDifferentYear() {

        // given
        int year = 2023;
        int month = 1;

        // when
        List<GoalType> createdGoalTypes = monthlyGoalQueryRepository.findCreatedGoalTypes(member1.getId(), year, month);

        // then
        assertThat(createdGoalTypes.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("맴버가 다른 경우")
    public void differentMember() {

        // given
        int year = 2022;
        int month = 1;

        // when
        List<GoalType> createdGoalTypes = monthlyGoalQueryRepository.findCreatedGoalTypes(member2.getId(), year, month);

        // then
        assertThat(createdGoalTypes.size()).isEqualTo(1);
        assertThat(createdGoalTypes.get(0)).isEqualTo(GoalType.A);
    }
}