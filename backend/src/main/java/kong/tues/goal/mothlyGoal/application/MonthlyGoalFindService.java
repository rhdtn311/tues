package kong.tues.goal.mothlyGoal.application;

import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalRepository;
import kong.tues.goal.exception.GoalNotFoundException;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalQueryRepository;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalRepository;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalMainResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.stream.Collectors;

import static kong.tues.goal.dailyGoal.domain.QDailyGoal.dailyGoal;

@RequiredArgsConstructor
@Service
public class MonthlyGoalFindService {

    private final MonthlyGoalQueryRepository monthlyGoalQueryRepository;
    private final DailyGoalRepository dailyGoalRepository;

    @Transactional(readOnly = true)
    public List<MonthlyGoalMainResDto> findMonthlyGoals(Long memberId, int year, int month) {

        return monthlyGoalQueryRepository.findAllMonthlyGoals(memberId, year, month);
    }

    @Transactional(readOnly = true)
    public List<MonthlyGoalMainResDto> getMonthlyGoalNames(Long dailyGoalId) {
        DailyGoal dailyGoal = dailyGoalRepository.findById(dailyGoalId).orElseThrow(GoalNotFoundException::new);

        return monthlyGoalQueryRepository.findAllMonthlyGoals(dailyGoal.getMember().getId(), dailyGoal.getDate().getYear(), dailyGoal.getDate().getMonthValue());
    }
}
