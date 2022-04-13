package kong.tues.goal.dailyGoal.application.dailyGoalManager;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalManager;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalQueryRepository;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalRepository;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalAchieveResDto;
import kong.tues.goal.exception.GoalNotFoundException;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalQueryRepository;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalAchieveResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyGoalManager implements GoalManager {

    private final DailyGoalRepository dailyGoalRepository;
    private final MonthlyGoalQueryRepository monthlyGoalQueryRepository;

    @Override
    public Map<String, Object> successGoal(Long memberId, Long goalId) {
        DailyGoal dailyGoal = dailyGoalRepository.findById(goalId).orElseThrow(GoalNotFoundException::new);
        MonthlyGoal monthlyGoal = monthlyGoalQueryRepository
                .findMonthlyGoalByGoalType(memberId, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), dailyGoal.getGoalType());

        if (dailyGoal.getAchieveType() == AchieveType.COUNT) {
            dailyGoal.plusGoalCount();
        } else if (dailyGoal.getAchieveType() == AchieveType.TIME) {
            dailyGoal.plusGoalTime();
        } else if (dailyGoal.getAchieveType() == AchieveType.BASIC) {
            dailyGoal.checkSuccess();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("dailyGoal", DailyGoalAchieveResDto.entityToDto(dailyGoal));
        if (monthlyGoal != null) {
            map.put("monthlyGoal", MonthlyGoalAchieveResDto.entityToDto(monthlyGoal));
        }

        return map;
    }

    @Override
    public Map<String, Object> failGoal(Long memberId, Long goalId) {
        DailyGoal dailyGoal = dailyGoalRepository.findById(goalId).orElseThrow(GoalNotFoundException::new);
        MonthlyGoal monthlyGoal = monthlyGoalQueryRepository
                .findMonthlyGoalByGoalType(memberId, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), dailyGoal.getGoalType());

        if (dailyGoal.getAchieveType() == AchieveType.COUNT) {
            dailyGoal.minusGoalCount();
        } else if (dailyGoal.getAchieveType() == AchieveType.TIME) {
            dailyGoal.minusGoalTime();
        } else if (dailyGoal.getAchieveType() == AchieveType.BASIC) {
            dailyGoal.checkSuccess();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("dailyGoal", DailyGoalAchieveResDto.entityToDto(dailyGoal));
        if (monthlyGoal != null) {
            map.put("monthlyGoal", MonthlyGoalAchieveResDto.entityToDto(monthlyGoal));
        }

        return map;
    }
}
