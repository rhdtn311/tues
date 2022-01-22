package kong.tues.goal.mothlyGoal.application.monthlyGoalManager;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalManager;
import kong.tues.goal.exception.GoalNotFoundException;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MonthlyGoalManager implements GoalManager {

    private final MonthlyGoalRepository monthlyGoalRepository;

    @Override
    public void successGoal(Long memberId, Long goalId) {
        MonthlyGoal monthlyGoal = monthlyGoalRepository.findById(goalId).orElseThrow(GoalNotFoundException::new);

        if (monthlyGoal.getAchieveType() == AchieveType.COUNT) {
            monthlyGoal.plusGoalCount();
        } else if (monthlyGoal.getAchieveType() == AchieveType.TIME) {
            monthlyGoal.plusGoalTime();
        } else if (monthlyGoal.getAchieveType() == AchieveType.BASIC) {
            monthlyGoal.checkSuccess();
        }
    }

    @Override
    public void failGoal(Long memberId, Long goalId) {
        MonthlyGoal monthlyGoal = monthlyGoalRepository.findById(goalId).orElseThrow(GoalNotFoundException::new);

        if (monthlyGoal.getAchieveType() == AchieveType.COUNT) {
            monthlyGoal.minusGoalCount();
        } else if (monthlyGoal.getAchieveType() == AchieveType.TIME) {
            monthlyGoal.minusGoalTime();
        } else if (monthlyGoal.getAchieveType() == AchieveType.BASIC) {
            monthlyGoal.checkSuccess();
        }
    }
}
