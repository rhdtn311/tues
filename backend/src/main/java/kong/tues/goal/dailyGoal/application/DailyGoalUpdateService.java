package kong.tues.goal.dailyGoal.application;


import kong.tues.goal.dailyGoal.application.dto.DailyGoalUpdateResDto;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalRepository;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalReqDto;
import kong.tues.goal.exception.GoalNotFoundException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DailyGoalUpdateService {

    private final DailyGoalRepository dailyGoalRepository;

    // 화면
    public DailyGoalUpdateResDto getDailyGoalUpdate(Long dailyGoalId) {
        DailyGoal dailyGoal
                = dailyGoalRepository.findById(dailyGoalId).orElseThrow(GoalNotFoundException::new);

        return DailyGoalUpdateResDto.toDto(dailyGoal);
    }

    // 로직
    @Transactional
    public Long updateDailyGoal(DailyGoalReqDto dailyGoalReqDto) {
        DailyGoal dailyGoal
                = dailyGoalRepository.findById(dailyGoalReqDto.getDailyGoalId()).orElseThrow(GoalNotFoundException::new);

        dailyGoal.update(dailyGoalReqDto);

        return dailyGoal.getId();
    }
}
