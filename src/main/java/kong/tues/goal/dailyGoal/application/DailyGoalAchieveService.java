package kong.tues.goal.dailyGoal.application;

import kong.tues.goal.dailyGoal.application.dailyGoalManager.DailyGoalManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DailyGoalAchieveService {

    private final DailyGoalManager dailyGoalManager;

    @Transactional
    public void achieveDailyGoal(Long memberId, Long dailyGoalId) {

        dailyGoalManager.successGoal(memberId, dailyGoalId);
    }

    @Transactional
    public void failDailyGoal(Long memberId, Long dailyGoalId) {

        dailyGoalManager.failGoal(memberId, dailyGoalId);
    }
}
