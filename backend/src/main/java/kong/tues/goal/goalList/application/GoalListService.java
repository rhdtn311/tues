package kong.tues.goal.goalList.application;

import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalQueryRepository;
import kong.tues.goal.goalList.application.dto.DailyGoalListDto;
import kong.tues.goal.goalList.application.dto.MonthlyGoalListDto;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoalListService {

    private final DailyGoalQueryRepository dailyGoalQueryRepository;

    @Transactional
    public List<List<List<DailyGoalListDto>>> getGoalList(int year, int month, Long memberId) {

        LocalDate date = LocalDate.of(year, month, 1);

        List<DailyGoalListDto> dailyGoals = dailyGoalQueryRepository.findAllDailyGoals(memberId, year, month);

        List<List<DailyGoalListDto>> weeklyGoals = new ArrayList<>();
        for (int day = 0; day < date.getDayOfWeek().getValue() - 1; day++) {
            weeklyGoals.add(null);
        }

        List<List<List<DailyGoalListDto>>> goalList = new ArrayList<>();
        List<DailyGoalListDto> dailyGoalList = new ArrayList<>();
        for (int day = 1; day <= date.lengthOfMonth(); day++) {
            dailyGoalList = new ArrayList<>();
            for (DailyGoalListDto dailyGoal : dailyGoals) {
                if (dailyGoal.getDay() == day) {
                    dailyGoalList.add(dailyGoal);
                }
            }
            weeklyGoals.add(dailyGoalList);
            if (weeklyGoals.size() == 7) {
                goalList.add(weeklyGoals);
                weeklyGoals = new ArrayList<>();
            }
        }

        if (weeklyGoals.size() != 0) {
            while (weeklyGoals.size() < 7) {
                weeklyGoals.add(null);
            }
        }

        goalList.add(weeklyGoals);

        return goalList;
    }

    public List<Integer> getDays(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        List<Integer> days = IntStream.rangeClosed(1, date.lengthOfMonth())
                .boxed()
                .collect(Collectors.toList());

        for (int day = 0; day < date.getDayOfWeek().getValue() - 1; day++) {
            days.add(0, 0);
        }

        return days;
    }
}
