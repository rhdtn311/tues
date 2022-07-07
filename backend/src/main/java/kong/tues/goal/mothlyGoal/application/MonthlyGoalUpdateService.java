package kong.tues.goal.mothlyGoal.application;

import kong.tues.goal.exception.GoalNotFoundException;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalRepository;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalReqDto;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalUpdateResDto;
import kong.tues.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MonthlyGoalUpdateService {

    private final MonthlyGoalRepository monthlyGoalRepository;

    // 화면
    public MonthlyGoalUpdateResDto getMonthlyGoalUpdate(Long monthlyGoalId) {

        MonthlyGoal monthlyGoal
                = monthlyGoalRepository.findById(monthlyGoalId).orElseThrow(GoalNotFoundException::new);

        return MonthlyGoalUpdateResDto.toDto(monthlyGoal);
    }

    @Transactional
    public void updateMonthlyGoal(MonthlyGoalReqDto monthlyGoalReqDto) {

        MonthlyGoal updateMonthlyGoal
                = monthlyGoalRepository.findById(monthlyGoalReqDto.getMonthlyGoalId()).orElseThrow(GoalNotFoundException::new);

        updateMonthlyGoal.update(monthlyGoalReqDto);
    }
}
