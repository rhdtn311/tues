package kong.tues.goal.mothlyGoal.application;

import kong.tues.goal.exception.GoalNotFoundException;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalRepository;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalAchieveResDto;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalMainResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MonthlyGoalAchieveService {

    private final MonthlyGoalRepository monthlyGoalRepository;
    private final MonthlyGoalFindService monthlyGoalFindService;

    @Transactional
    public List<MonthlyGoalMainResDto> minusMonthlyGoal(Long monthlyGoalId) {
        MonthlyGoal monthlyGoal = monthlyGoalRepository.findById(monthlyGoalId).orElseThrow(GoalNotFoundException::new);

        if (monthlyGoal.isBasic()) {
            monthlyGoal.checkSuccess();
        }

        if (monthlyGoal.isCount()) {
            monthlyGoal.minusGoalCount();
        }

        if (monthlyGoal.isTime()) {
            monthlyGoal.minusGoalTime();
        }

        return monthlyGoalFindService.findMonthlyGoals(monthlyGoal.getMember().getId(), LocalDate.now().getYear(), LocalDate.now().getMonthValue());
    }

    @Transactional
    public List<MonthlyGoalMainResDto> plusMonthlyGoal(Long monthlyGoalId) {
        MonthlyGoal monthlyGoal = monthlyGoalRepository.findById(monthlyGoalId).orElseThrow(GoalNotFoundException::new);

        if (monthlyGoal.isBasic()) {
            monthlyGoal.checkSuccess();
        }

        if (monthlyGoal.isCount()) {
            monthlyGoal.plusGoalCount();
        }

        if (monthlyGoal.isTime()) {
            monthlyGoal.plusGoalTime();
        }

        return monthlyGoalFindService.findMonthlyGoals(monthlyGoal.getMember().getId(), LocalDate.now().getYear(), LocalDate.now().getMonthValue());
    }
}
