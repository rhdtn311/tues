package kong.tues.goal.dailyGoal.application.dailyGoalManager;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalRepository;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DailyGoalManagerTest {

    @InjectMocks
    private DailyGoalManager dailyGoalManager;

    @Mock
    private DailyGoalRepository dailyGoalRepository;

    @Mock
    private MonthlyGoalQueryRepository monthlyGoalQueryRepository;

    @Test
    @DisplayName("AchieveType이 COUNT인 경우")
    void achieveTypeCount() {

        // given
        DailyGoal dailyGoal = DailyGoal.builder()
                .achieveType(AchieveType.COUNT)
                .goalType(GoalType.A)
                .name("일간목표A")
                .goalCountQuota(100)
                .goalCount(19)
                .build();

        MonthlyGoal monthlyGoal = MonthlyGoal.builder()
                .achieveType(AchieveType.COUNT)
                .goalType(GoalType.A)
                .name("월간 목표 A")
                .goalCountQuota(1000)
                .goalCount(40)
                .build();

        doReturn(Optional.of(dailyGoal)).when(dailyGoalRepository).findById(any());
        doReturn(monthlyGoal).when(monthlyGoalQueryRepository).findMonthlyGoalByGoalType(any(Long.class), any(Integer.class), any(Integer.class), any(GoalType.class));

        // when
        dailyGoalManager.successGoal(1L,1L);

        // then
        assertThat(dailyGoal.getGoalCount()).isEqualTo(20);
    }

    @Test
    @DisplayName("AchieveType이 TIME인 경우")
    void achieveTypeTime() {

        // given
        DailyGoal dailyGoal = DailyGoal.builder()
                .achieveType(AchieveType.TIME)
                .goalType(GoalType.A)
                .name("일간목표A")
                .goalTimeQuota(10)
                .goalTime(3)
                .build();

        MonthlyGoal monthlyGoal = MonthlyGoal.builder()
                .achieveType(AchieveType.COUNT)
                .goalType(GoalType.A)
                .name("월간 목표 A")
                .goalCountQuota(1000)
                .goalCount(40)
                .build();

        doReturn(Optional.of(dailyGoal)).when(dailyGoalRepository).findById(any());
        doReturn(monthlyGoal).when(monthlyGoalQueryRepository).findMonthlyGoalByGoalType(any(Long.class), any(Integer.class), any(Integer.class), any(GoalType.class));

        // when
        dailyGoalManager.successGoal(1L,1L);

        // then
        assertThat(dailyGoal.getGoalTime()).isEqualTo(4);
    }

    @Test
    @DisplayName("AchieveType이 WAKE인 경우")
    void achieveTypeWake() {

        // given
        DailyGoal dailyGoal = DailyGoal.builder()
                .achieveType(AchieveType.WAKE)
                .goalType(GoalType.A)
                .name("일간목표A")
                .wakeUpTime(LocalTime.of(23, 59))
                .build();

        MonthlyGoal monthlyGoal = MonthlyGoal.builder()
                .achieveType(AchieveType.COUNT)
                .goalType(GoalType.A)
                .name("월간 목표 A")
                .goalCountQuota(1000)
                .goalCount(40)
                .build();

        doReturn(Optional.of(dailyGoal)).when(dailyGoalRepository).findById(any());
        doReturn(monthlyGoal).when(monthlyGoalQueryRepository).findMonthlyGoalByGoalType(any(Long.class), any(Integer.class), any(Integer.class), any(GoalType.class));

        // when
        dailyGoalManager.successGoal(1L, 1L);

        // then
        assertThat(dailyGoal.getSuccess()).isTrue();
    }

    @Test
    @DisplayName("AchieveType이 BASIC인 경우")
    void achieveTypeBASIC() {

        // given
        DailyGoal dailyGoal = DailyGoal.builder()
                .achieveType(AchieveType.BASIC)
                .goalType(GoalType.A)
                .name("일간목표A")
                .build();

        MonthlyGoal monthlyGoal = MonthlyGoal.builder()
                .achieveType(AchieveType.COUNT)
                .goalType(GoalType.A)
                .name("월간 목표 A")
                .goalCountQuota(1000)
                .goalCount(40)
                .build();

        doReturn(Optional.of(dailyGoal)).when(dailyGoalRepository).findById(any());
        doReturn(monthlyGoal).when(monthlyGoalQueryRepository).findMonthlyGoalByGoalType(any(Long.class), any(Integer.class), any(Integer.class), any(GoalType.class));

        // when
        dailyGoalManager.successGoal(1L, 1L);

        // then
        assertThat(dailyGoal.getSuccess()).isTrue();
    }


}