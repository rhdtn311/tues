package kong.tues.goal.mothlyGoal.application;

import kong.tues.goal.mothlyGoal.application.monthlyGoalManager.MonthlyGoalManager;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalAchieveResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MonthlyGoalAchieveService {

    private final MonthlyGoalManager monthlyGoalManager;

    @Transactional
    public MonthlyGoalAchieveResDto achieveMonthlyGoal(Long memberId, Long monthlyGoalId) {

        return monthlyGoalManager.successGoal(memberId, monthlyGoalId);
    }

    @Transactional
    public MonthlyGoalAchieveResDto failMonthlyGoal(Long memberId, Long monthlyGoalId) {

        return monthlyGoalManager.failGoal(memberId, monthlyGoalId);
    }
}
