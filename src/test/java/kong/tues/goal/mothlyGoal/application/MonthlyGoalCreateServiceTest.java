package kong.tues.goal.mothlyGoal.application;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.exception.GoalTypeDupException;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalRepository;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalReqDto;
import kong.tues.member.domain.Member;
import kong.tues.member.domain.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MonthlyGoalCreateServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MonthlyGoalCreateService monthlyGoalCreateService;

    @Autowired
    private MonthlyGoalRepository monthlyGoalRepository;

    static Member member1;
    static Member member2;

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
    }


    @Test
    @DisplayName("월간 목표 저장 성공")
    void saveMonthlyGoal() {

        // given
        MonthlyGoalReqDto monthlyGoalReqDto1 = MonthlyGoalReqDto.builder()
                .name("책 10권 이상 읽기")
                .content("매일 책을 읽은 습관을 기르기 위해 하루도 빠짐 없이 독서를 한다.")
                .goalType(GoalType.A)
                .achieveType(AchieveType.COUNT)
                .goalCountQuota(10)
                .goalCount(0)
                .goalTimeQuota(null)
                .goalTime(0)
                .year(2022)
                .month(1)
                .success(false)
                .build();

        MonthlyGoalReqDto monthlyGoalReqDto2 = MonthlyGoalReqDto.builder()
                .name("인터넷 강의 100시간 듣기")
                .content("구입해놨던 강의를 모두 듣는다.")
                .goalType(GoalType.B)
                .achieveType(AchieveType.COUNT)
                .goalCountQuota(null)
                .goalCount(0)
                .goalTimeQuota(100)
                .goalTime(0)
                .year(2022)
                .month(1)
                .success(false)
                .build();

        // when
        Long monthlyGoalId1 = monthlyGoalCreateService.save(monthlyGoalReqDto1, member1.getId());
        Long monthlyGoalId2 = monthlyGoalCreateService.save(monthlyGoalReqDto2, member1.getId());

        MonthlyGoal monthlyGoal1 = monthlyGoalRepository.findById(monthlyGoalId1).get();
        MonthlyGoal monthlyGoal2 = monthlyGoalRepository.findById(monthlyGoalId2).get();

        // then
        assertThat(monthlyGoal1.getName()).isEqualTo(monthlyGoalReqDto1.getName());
        assertThat(monthlyGoal1.getContent()).isEqualTo(monthlyGoalReqDto1.getContent());
        assertThat(monthlyGoal1.getGoalType()).isEqualTo(monthlyGoalReqDto1.getGoalType());
        assertThat(monthlyGoal1.getAchieveType()).isEqualTo(monthlyGoalReqDto1.getAchieveType());
        assertThat(monthlyGoal1.getGoalCountQuota()).isEqualTo(monthlyGoalReqDto1.getGoalCountQuota());
        assertThat(monthlyGoal1.getGoalCount()).isEqualTo(monthlyGoalReqDto1.getGoalCount());
        assertThat(monthlyGoal1.getGoalTimeQuota()).isEqualTo(monthlyGoalReqDto1.getGoalTimeQuota());
        assertThat(monthlyGoal1.getGoalTime()).isEqualTo(monthlyGoalReqDto1.getGoalTime());
        assertThat(monthlyGoal1.getDate().getYear()).isEqualTo(monthlyGoalReqDto1.getYear());
        assertThat(monthlyGoal1.getDate().getMonth().getValue()).isEqualTo(monthlyGoalReqDto1.getMonth());
        assertThat(monthlyGoal1.getSuccess()).isEqualTo(monthlyGoalReqDto1.getSuccess());

        assertThat(monthlyGoal2.getName()).isEqualTo(monthlyGoalReqDto2.getName());
        assertThat(monthlyGoal2.getContent()).isEqualTo(monthlyGoalReqDto2.getContent());
        assertThat(monthlyGoal2.getGoalType()).isEqualTo(monthlyGoalReqDto2.getGoalType());
        assertThat(monthlyGoal2.getAchieveType()).isEqualTo(monthlyGoalReqDto2.getAchieveType());
        assertThat(monthlyGoal2.getGoalCountQuota()).isEqualTo(monthlyGoalReqDto2.getGoalCountQuota());
        assertThat(monthlyGoal2.getGoalCount()).isEqualTo(monthlyGoalReqDto2.getGoalCount());
        assertThat(monthlyGoal2.getGoalTimeQuota()).isEqualTo(monthlyGoalReqDto2.getGoalTimeQuota());
        assertThat(monthlyGoal2.getGoalTime()).isEqualTo(monthlyGoalReqDto2.getGoalTime());
        assertThat(monthlyGoal2.getDate().getYear()).isEqualTo(monthlyGoalReqDto2.getYear());
        assertThat(monthlyGoal2.getDate().getMonth().getValue()).isEqualTo(monthlyGoalReqDto2.getMonth());
        assertThat(monthlyGoal2.getSuccess()).isEqualTo(monthlyGoalReqDto2.getSuccess());
    }

    @Test
    @DisplayName("이미 회원이 해당 종류의 목표를 가지고 있을 경우")
    void existsThatKindOfGoalType() {

        // given
        MonthlyGoalReqDto monthlyGoalReqDto1 = MonthlyGoalReqDto.builder()
                .name("책 10권 이상 읽기")
                .content("매일 책을 읽은 습관을 기르기 위해 하루도 빠짐 없이 독서를 한다.")
                .goalType(GoalType.A)
                .achieveType(AchieveType.COUNT)
                .goalCountQuota(10)
                .goalCount(0)
                .goalTimeQuota(null)
                .goalTime(0)
                .year(2022)
                .month(1)
                .success(false)
                .build();

        monthlyGoalCreateService.save(monthlyGoalReqDto1, member1.getId());

        // when
        MonthlyGoalReqDto monthlyGoalReqDto2 = MonthlyGoalReqDto.builder()
                .name("수학 문제 100문제 이상 풀기")
                .content("시험 대비를 위해 매일 수학 문제를 푼다.")
                .goalType(GoalType.A)
                .achieveType(AchieveType.COUNT)
                .goalCountQuota(100)
                .goalCount(0)
                .goalTimeQuota(null)
                .goalTime(0)
                .year(2022)
                .month(1)
                .success(false)
                .build();

        // then
        assertThrows(GoalTypeDupException.class, () -> {
            monthlyGoalCreateService.save(monthlyGoalReqDto2, member1.getId());
        });
    }

    @Test
    @DisplayName("회원이 같은 종류의 목표를 가지고 있지만 월이 다른 경우")
    void existsThatKindOfGoalTypeDifferentMonth() {
        // given
        MonthlyGoalReqDto monthlyGoalReqDto1 = MonthlyGoalReqDto.builder()
                .name("책 10권 이상 읽기")
                .content("매일 책을 읽은 습관을 기르기 위해 하루도 빠짐 없이 독서를 한다.")
                .goalType(GoalType.A)
                .achieveType(AchieveType.COUNT)
                .goalCountQuota(10)
                .goalCount(0)
                .goalTimeQuota(null)
                .goalTime(0)
                .year(2022)
                .month(1)
                .success(false)
                .build();

        monthlyGoalCreateService.save(monthlyGoalReqDto1, member1.getId());

        // when
        MonthlyGoalReqDto monthlyGoalReqDto2 = MonthlyGoalReqDto.builder()
                .name("수학 문제 100문제 이상 풀기")
                .content("시험 대비를 위해 매일 수학 문제를 푼다.")
                .goalType(GoalType.A)
                .achieveType(AchieveType.COUNT)
                .goalCountQuota(100)
                .goalCount(0)
                .goalTimeQuota(null)
                .goalTime(0)
                .year(2022)
                .month(2)
                .success(false)
                .build();

        // then
        monthlyGoalCreateService.save(monthlyGoalReqDto2, member1.getId());
    }

    @Test
    @DisplayName("회원이 같은 종류의 목표를 가지고 있고, 월이 같지만 년도가 다른 경우")
    void existsThatKindOfGoalTypeSameMonthDifferentYear() {
        // given
        MonthlyGoalReqDto monthlyGoalReqDto1 = MonthlyGoalReqDto.builder()
                .name("책 10권 이상 읽기")
                .content("매일 책을 읽은 습관을 기르기 위해 하루도 빠짐 없이 독서를 한다.")
                .goalType(GoalType.A)
                .achieveType(AchieveType.COUNT)
                .goalCountQuota(10)
                .goalCount(0)
                .goalTimeQuota(null)
                .goalTime(0)
                .year(2022)
                .month(1)
                .success(false)
                .build();

        monthlyGoalCreateService.save(monthlyGoalReqDto1, member1.getId());

        // when
        MonthlyGoalReqDto monthlyGoalReqDto2 = MonthlyGoalReqDto.builder()
                .name("수학 문제 100문제 이상 풀기")
                .content("시험 대비를 위해 매일 수학 문제를 푼다.")
                .goalType(GoalType.A)
                .achieveType(AchieveType.COUNT)
                .goalCountQuota(100)
                .goalCount(0)
                .goalTimeQuota(null)
                .goalTime(0)
                .year(2023)
                .month(1)
                .success(false)
                .build();

        // then
        monthlyGoalCreateService.save(monthlyGoalReqDto2, member1.getId());
    }

    @Rollback(value = false)
    @Test
    @DisplayName("같은 년도 같은 월에 생성된 목표가 같은 종류지만 생성한 회원이 다른 경우")
    void sameGoalTypeButDifferentMember() {

        // when
        MonthlyGoalReqDto monthlyGoalReqDto1 = MonthlyGoalReqDto.builder()
                .name("책 10권 이상 읽기")
                .content("매일 책을 읽은 습관을 기르기 위해 하루도 빠짐 없이 독서를 한다.")
                .goalType(GoalType.A)
                .achieveType(AchieveType.COUNT)
                .goalCountQuota(10)
                .goalCount(0)
                .goalTimeQuota(null)
                .goalTime(0)
                .year(2022)
                .month(1)
                .success(false)
                .build();

        // then
        monthlyGoalCreateService.save(monthlyGoalReqDto1, member1.getId());
        monthlyGoalCreateService.save(monthlyGoalReqDto1, member2.getId());
    }


}