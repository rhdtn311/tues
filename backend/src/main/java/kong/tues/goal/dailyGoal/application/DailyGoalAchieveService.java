package kong.tues.goal.dailyGoal.application;

import kong.tues.goal.GoalType;
import kong.tues.goal.dailyGoal.application.dto.DailyGoalMainResDto;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalRepository;
import kong.tues.goal.exception.GoalNotFoundException;
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

    private final MonthlyGoalQueryRepository monthlyGoalQueryRepository;

    private final DailyGoalRepository dailyGoalRepository;

    @Transactional
    public DailyGoal minusDailyGoal(Long dailyGoalId) {

        DailyGoal dailyGoal = dailyGoalRepository.findById(dailyGoalId).orElseThrow(GoalNotFoundException::new);
        MonthlyGoal monthlyGoal = dailyGoal.getMonthlyGoal();
        if (dailyGoal.isCount()) {
            if (monthlyGoal != null && isSameAchieveType(dailyGoal, monthlyGoal)) {
                monthlyGoal.minusGoalCount();
            }
            dailyGoal.minusGoalCount();
        } else if (dailyGoal.isTime()) {
            if (monthlyGoal != null && isSameAchieveType(dailyGoal, monthlyGoal)) {
                monthlyGoal.minusGoalTime();
            }
            dailyGoal.minusGoalTime();
        } else if (dailyGoal.isBasic()) {
            dailyGoal.checkSuccess();
        }

        return dailyGoal;
    }

    @Transactional
    public DailyGoal plusDailyGoal(Long dailyGoalId) {

        DailyGoal dailyGoal = dailyGoalRepository.findById(dailyGoalId).orElseThrow(GoalNotFoundException::new);
        MonthlyGoal monthlyGoal = dailyGoal.getMonthlyGoal();
        if (dailyGoal.isCount()) {
            if (monthlyGoal != null && isSameAchieveType(dailyGoal, monthlyGoal)) {
                monthlyGoal.plusGoalCount();
            }
            dailyGoal.plusGoalCount();
        } else if (dailyGoal.isTime()) {
            if (monthlyGoal != null && isSameAchieveType(dailyGoal, monthlyGoal)) {
                monthlyGoal.plusGoalTime();
            }
            dailyGoal.plusGoalTime();
        } else if (dailyGoal.isBasic()) {
            dailyGoal.checkSuccess();
        }

        return dailyGoal;
    }

    private boolean isSameAchieveType(DailyGoal dailyGoal, MonthlyGoal monthlyGoal) {
        return dailyGoal.getAchieveType() == monthlyGoal.getAchieveType();
    }

    public MonthlyGoal findMonthlyGoalIdByGoalType(Long memberId, GoalType goalType) {

        return monthlyGoalQueryRepository.findMonthlyGoalByGoalType(memberId, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), goalType);
    }
}
