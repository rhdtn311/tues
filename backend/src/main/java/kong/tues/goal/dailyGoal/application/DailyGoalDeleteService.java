package kong.tues.goal.dailyGoal.application;

import kong.tues.goal.AchieveType;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalRepository;
import kong.tues.goal.exception.GoalNotFoundException;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DailyGoalDeleteService {

    private final DailyGoalRepository dailyGoalRepository;
    private final MonthlyGoalQueryRepository monthlyGoalQueryRepository;

    @Transactional
    public Long deleteDailyGoal(Long dailyGoalId) {

        DailyGoal dailyGoal
                = dailyGoalRepository.findById(dailyGoalId).orElseThrow(GoalNotFoundException::new);
        MonthlyGoal monthlyGoal = dailyGoal.getMonthlyGoal();

        if (monthlyGoal != null) {
            minusMonthlyGoal(monthlyGoal, dailyGoal.getAchieveType(), dailyGoal.getAchieveType() == AchieveType.COUNT ? dailyGoal.getGoalCount() : dailyGoal.getGoalTime());
        }

        dailyGoalRepository.delete(dailyGoal);

        return dailyGoal.getId();
    }

    public void minusMonthlyGoal(MonthlyGoal monthlyGoal , AchieveType achieveType, Integer minusValue) {
        if (achieveType == AchieveType.COUNT) {
            monthlyGoal.minusGoalCounts(minusValue);
        } else if (achieveType == AchieveType.TIME) {
            monthlyGoal.minusGoalTimes(minusValue);
        }
    }
}
