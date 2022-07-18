package kong.tues.goal.mothlyGoal.application;

import kong.tues.goal.exception.GoalNotFoundException;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MonthlyGoalDeleteService {

    private final MonthlyGoalRepository monthlyGoalRepository;

    @Transactional
    public Long deleteMonthlyGoal(Long monthlyGoalId) {

        MonthlyGoal monthlyGoal
                = monthlyGoalRepository.findById(monthlyGoalId).orElseThrow(GoalNotFoundException::new);

        monthlyGoal.getDailyGoals().stream()
                        .forEach(dailyGoal -> dailyGoal.setMonthlyGoal(null));

        monthlyGoalRepository.delete(monthlyGoal);

        return monthlyGoalId;
    }
}
