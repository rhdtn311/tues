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
    public Long updateMonthlyGoal(MonthlyGoalReqDto monthlyGoalReqDto) {

        MonthlyGoal updateMonthlyGoal
                = monthlyGoalRepository.findById(monthlyGoalReqDto.getMonthlyGoalId()).orElseThrow(GoalNotFoundException::new);

        removeDailyGoalRelative(updateMonthlyGoal, monthlyGoalReqDto);
        createDailyGoalRelative(updateMonthlyGoal, monthlyGoalReqDto);

        return updateMonthlyGoal.update(monthlyGoalReqDto).getId();
    }

    private void removeDailyGoalRelative(MonthlyGoal monthlyGoal, MonthlyGoalReqDto monthlyGoalReqDto) {
        monthlyGoal.getDailyGoals()
                .stream()
                .filter(dailyGoal -> {
                    return dailyGoal.getDate().getYear() == monthlyGoal.getDate().getYear() &&
                            dailyGoal.getDate().getMonth() == monthlyGoal.getDate().getMonth() &&
                            dailyGoal.getGoalType() == monthlyGoal.getGoalType();
                })
                .forEach(dailyGoal -> {
                    if (dailyGoal.getDate().getYear() != monthlyGoalReqDto.getYear() ||
                            dailyGoal.getDate().getMonthValue() != monthlyGoalReqDto.getMonth() ||
                            dailyGoal.getGoalType() != monthlyGoalReqDto.getGoalType()) {
                        dailyGoal.setMonthlyGoal(null);
                    }
                });
    }

    private void createDailyGoalRelative(MonthlyGoal monthlyGoal, MonthlyGoalReqDto monthlyGoalReqDto) {
        monthlyGoal.getMember().getDailyGoal()
                .stream()
                .filter(dailyGoal -> {
                    return dailyGoal.getDate().getYear() == monthlyGoalReqDto.getYear() &&
                            dailyGoal.getDate().getMonthValue() == monthlyGoalReqDto.getMonth() &&
                            dailyGoal.getGoalType() == monthlyGoalReqDto.getGoalType();
                }).forEach(dailyGoal -> dailyGoal.setMonthlyGoal(monthlyGoal));
    }
}
