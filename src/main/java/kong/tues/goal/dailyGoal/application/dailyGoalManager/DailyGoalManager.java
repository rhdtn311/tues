package kong.tues.goal.dailyGoal.application.dailyGoalManager;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalManager;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalQueryRepository;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalRepository;
import kong.tues.goal.exception.GoalNotFoundException;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyGoalManager implements GoalManager {

    private final DailyGoalRepository dailyGoalRepository;
    private final MonthlyGoalQueryRepository monthlyGoalQueryRepository;

    @Override
    public void successGoal(Long memberId, Long goalId) {
        DailyGoal dailyGoal = dailyGoalRepository.findById(goalId).orElseThrow(GoalNotFoundException::new);
        MonthlyGoal monthlyGoal = monthlyGoalQueryRepository
                .findMonthlyGoalByGoalType(memberId, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), dailyGoal.getGoalType());

        if (dailyGoal.getAchieveType() == AchieveType.COUNT) {
            dailyGoal.plusGoalCount();
            if (monthlyGoal != null && dailyGoal.getAchieveType() == monthlyGoal.getAchieveType()) {
                monthlyGoal.plusGoalCount();
            }
        } else if (dailyGoal.getAchieveType() == AchieveType.TIME) {
            dailyGoal.plusGoalTime();
            if (monthlyGoal != null && dailyGoal.getAchieveType() == monthlyGoal.getAchieveType()) {
                monthlyGoal.plusGoalTime();
            }
        } else if (dailyGoal.getAchieveType() == AchieveType.WAKE) {
            dailyGoal.checkSuccess();
        } else if (dailyGoal.getAchieveType() == AchieveType.BASIC) {
            dailyGoal.checkSuccess();
            if (monthlyGoal != null && dailyGoal.getAchieveType() == monthlyGoal.getAchieveType()) {
                monthlyGoal.checkSuccess();
            }
        }
    }

    @Override
    public void failGoal(Long memberId, Long goalId) {
        DailyGoal dailyGoal = dailyGoalRepository.findById(goalId).orElseThrow(GoalNotFoundException::new);
        MonthlyGoal monthlyGoal = monthlyGoalQueryRepository
                .findMonthlyGoalByGoalType(memberId, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), dailyGoal.getGoalType());

        if (dailyGoal.getAchieveType() == AchieveType.COUNT) {
            dailyGoal.minusGoalCount();
            if (monthlyGoal != null && dailyGoal.getAchieveType() == monthlyGoal.getAchieveType()) {
                monthlyGoal.minusGoalCount();
            }
        } else if (dailyGoal.getAchieveType() == AchieveType.TIME) {
            dailyGoal.minusGoalTime();
            if (monthlyGoal != null && dailyGoal.getAchieveType() == monthlyGoal.getAchieveType()) {
                monthlyGoal.minusGoalTime();
            }
        } else if (dailyGoal.getAchieveType() == AchieveType.WAKE) {
            dailyGoal.checkSuccess();
        } else if (dailyGoal.getAchieveType() == AchieveType.BASIC) {
            dailyGoal.checkSuccess();
            if (monthlyGoal != null && dailyGoal.getAchieveType() == monthlyGoal.getAchieveType()) {
                monthlyGoal.checkSuccess();
            }
        }
    }
}
