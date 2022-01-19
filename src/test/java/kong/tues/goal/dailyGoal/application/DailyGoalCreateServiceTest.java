package kong.tues.goal.dailyGoal.application;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.dailyGoal.application.dto.CreatedMonthlyGoalResDto;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalRepository;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalReqDto;
import kong.tues.goal.dailyGoal.presentation.validator.DailyGoalReqDtoValidator;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalQueryRepository;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalReqDto;
import kong.tues.member.domain.Member;
import kong.tues.member.domain.repository.MemberRepository;
import kong.tues.member.exception.MemberNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class DailyGoalCreateServiceTest {

    @InjectMocks
    private DailyGoalCreateService dailyGoalCreateService;

    @Mock
    private DailyGoalRepository dailyGoalRepository;

    @Mock
    private MemberRepository memberRepository;

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
    }

    @Test
    @DisplayName("일간 목표 저장 성공")
    public void saveCOUNT() {

        //given
        DailyGoalReqDto countTypeDailyGoalDto = DailyGoalReqDto.builder()
                .name("수학 문제 3문제 풀기")
                .goalType(GoalType.A)
                .goalCountQuota(100)
                .goalCount(0)
                .achieveType(AchieveType.COUNT)
                .content("시험대비를 위해 수학 문제를 푼다.")
                .year(2022)
                .month(1)
                .day(18)
                .build();

        // when
        doReturn(Optional.of(new Member())).when(memberRepository).findById(any(Long.class));
        doReturn(DailyGoal.builder().name(countTypeDailyGoalDto.getName()).goalType(countTypeDailyGoalDto.getGoalType())
                .goalCountQuota(countTypeDailyGoalDto.getGoalCountQuota()).goalCount(countTypeDailyGoalDto.getGoalCount())
                .achieveType(countTypeDailyGoalDto.getAchieveType()).content(countTypeDailyGoalDto.getContent())
                .date(LocalDate.of(countTypeDailyGoalDto.getYear(), countTypeDailyGoalDto.getMonth(), countTypeDailyGoalDto.getDay()))
                .member(member1).build()).when(dailyGoalRepository).save(any(DailyGoal.class));

        final DailyGoal countTypeDailyGoal = dailyGoalCreateService.save(countTypeDailyGoalDto, 1L);

        // then
        assertThat(countTypeDailyGoalDto.getName()).isEqualTo(countTypeDailyGoal.getName());
        assertThat(countTypeDailyGoalDto.getName()).isEqualTo(countTypeDailyGoal.getName());
        assertThat(countTypeDailyGoalDto.getGoalType()).isEqualTo(countTypeDailyGoal.getGoalType());
        assertThat(countTypeDailyGoalDto.getGoalCountQuota()).isEqualTo(countTypeDailyGoal.getGoalCountQuota());
        assertThat(countTypeDailyGoalDto.getGoalCount()).isEqualTo(countTypeDailyGoal.getGoalCount());
        assertThat(countTypeDailyGoalDto.getAchieveType()).isEqualTo(countTypeDailyGoal.getAchieveType());
        assertThat(countTypeDailyGoalDto.getContent()).isEqualTo(countTypeDailyGoal.getContent());
        assertThat(countTypeDailyGoalDto.getYear()).isEqualTo(countTypeDailyGoal.getDate().getYear());
        assertThat(countTypeDailyGoalDto.getMonth()).isEqualTo(countTypeDailyGoal.getDate().getMonthValue());
    }

    @Test
    @DisplayName("MonthlyGoalResDtoList를 HashMap으로 변환하는 monthlyGoalsToMap() 메서드 테스트")
    void monthlyGoalsToMap() {

        // given
        CreatedMonthlyGoalResDto createdMonthlyGoalResDto1 = new CreatedMonthlyGoalResDto();
        createdMonthlyGoalResDto1.setGoalType(GoalType.A);
        createdMonthlyGoalResDto1.setName("수학 문제 100문제 풀기");

        CreatedMonthlyGoalResDto createdMonthlyGoalResDto2 = new CreatedMonthlyGoalResDto();
        createdMonthlyGoalResDto2.setGoalType(GoalType.C);
        createdMonthlyGoalResDto2.setName("아침 10시에 기상하기");

        List<CreatedMonthlyGoalResDto> list = List.of(createdMonthlyGoalResDto1, createdMonthlyGoalResDto2);

        // when
        Map<String, String> map = dailyGoalCreateService.monthlyGoalsToMap(list);

        // then
        assertThat(map.size()).isEqualTo(2);
        assertThat(map.get(createdMonthlyGoalResDto1.getGoalType().toString())).isEqualTo(createdMonthlyGoalResDto1.getName());
        assertThat(map.get(createdMonthlyGoalResDto2.getGoalType().toString())).isEqualTo(createdMonthlyGoalResDto2.getName());

    }
}