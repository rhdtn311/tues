package kong.tues.goal.dailyGoal.application;

import kong.tues.goal.dailyGoal.application.dto.DailyGoalDetailResDto;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalRepository;
import kong.tues.goal.exception.GoalNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DailyGoalDetailService {

    private final DailyGoalRepository dailyGoalRepository;

    public DailyGoalDetailResDto getDailyGoalDetail(Long dailyGoalId) {

        DailyGoal dailyGoal
                = dailyGoalRepository.findById(dailyGoalId).orElseThrow(GoalNotFoundException::new);

        return DailyGoalDetailResDto.toDto(dailyGoal);
    }
}
