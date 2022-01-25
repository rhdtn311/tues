package kong.tues.goal.dailyGoal.application;

import kong.tues.goal.GoalType;
import kong.tues.goal.dailyGoal.application.dailyGoalManager.DailyGoalManager;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class DailyGoalAchieveService {

    private final DailyGoalManager dailyGoalManager;
    private final MonthlyGoalQueryRepository monthlyGoalQueryRepository;

    @Transactional
    public Map<String, Object> achieveDailyGoal(Long memberId, Long dailyGoalId) {

        return dailyGoalManager.successGoal(memberId, dailyGoalId);
    }

    @Transactional
    public Map<String, Object> failDailyGoal(Long memberId, Long dailyGoalId) {

        return dailyGoalManager.failGoal(memberId, dailyGoalId);
    }

    public MonthlyGoal findMonthlyGoalIdByGoalType(Long memberId, GoalType goalType) {

        return monthlyGoalQueryRepository.findMonthlyGoalByGoalType(memberId, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), goalType);
    }
}
