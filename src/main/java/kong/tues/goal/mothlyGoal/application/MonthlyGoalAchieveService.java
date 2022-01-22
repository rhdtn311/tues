package kong.tues.goal.mothlyGoal.application;

import kong.tues.goal.mothlyGoal.application.monthlyGoalManager.MonthlyGoalManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MonthlyGoalAchieveService {

    private final MonthlyGoalManager monthlyGoalManager;

    @Transactional
    public void achieveMonthlyGoal(Long memberId, Long monthlyGoalId) {

        monthlyGoalManager.successGoal(memberId, monthlyGoalId);
    }

    @Transactional
    public void failDailyGoal(Long memberId, Long dailyGoalId) {

        monthlyGoalManager.failGoal(memberId, dailyGoalId);
    }
}
