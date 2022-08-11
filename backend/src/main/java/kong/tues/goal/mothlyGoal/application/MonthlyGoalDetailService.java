package kong.tues.goal.mothlyGoal.application;

import kong.tues.goal.exception.GoalNotFoundException;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalRepository;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalDetailResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MonthlyGoalDetailService {

    private final MonthlyGoalRepository monthlyGoalRepository;

    @Transactional(readOnly = true)
    public MonthlyGoalDetailResDto getMonthlyGoalDetail(Long monthlyGoalId) {
        MonthlyGoal monthlyGoal
                = monthlyGoalRepository.findById(monthlyGoalId).orElseThrow(GoalNotFoundException::new);

        return MonthlyGoalDetailResDto.toDto(monthlyGoal);
    }
}
