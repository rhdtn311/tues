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

@Slf4j
@RequiredArgsConstructor
@Service
public class GoalListService {

    private final DailyGoalQueryRepository dailyGoalQueryRepository;
    private final MonthlyGoalQueryRepository monthlyGoalQueryRepository;

    // 해당 년도, 해당 월의 모든 일간 목표를 가져옴
    @Transactional
    public List<List<DailyGoalListDto>> getDailyGoalList(Long memberId, int year, int month, int lastDay) {

        List<DailyGoalListDto> findList = dailyGoalQueryRepository.findAllDailyGoals(memberId, year, month);
        addDayAndDayOfMonth(year, month, findList);

        List<List<DailyGoalListDto>> addAllDayOfCalendar = addAllDayOfCalendar(year, month, lastDay, findList);

        return addAllDayOfCalendar;
    }

    // 해당 년도, 해당 월의 모든 월간 목표를 가져옴
    @Transactional
    public List<MonthlyGoalListDto> getMonthlyGoalList(Long memberId, int year, int month) {

        return monthlyGoalQueryRepository.findAllMonthlyGoalsByDate(memberId, year, month);
    }

    public List<List<DailyGoalListDto>> addAllDayOfCalendar(int year, int month, int lastDay, List<DailyGoalListDto> dailyGoalList) {

        List<List<DailyGoalListDto>> allDailyGoalListDtoCalendar = new ArrayList<>();
        for (int i = 0; i <= lastDay; i++) {
            List<DailyGoalListDto> tempList = new ArrayList<>();
            if (i != 0) {
                LocalDate date = LocalDate.of(year, month, i);
                tempList.add(DailyGoalListDto.builder().day(i).hasGoal(false).dayOfWeek(date.getDayOfWeek().toString()).build());
            }
            allDailyGoalListDtoCalendar.add(tempList);
        }

        for (DailyGoalListDto dto : dailyGoalList) {
            if (!allDailyGoalListDtoCalendar.get(dto.getDay()).get(0).isHasGoal()) {
                allDailyGoalListDtoCalendar.get(dto.getDay()).remove(0);
            }
            allDailyGoalListDtoCalendar.get(dto.getDay()).add(dto);
        }

        return allDailyGoalListDtoCalendar;
    }

    public void addDayAndDayOfMonth(int year, int month, List<DailyGoalListDto> dailyGoalList) {
        for (int i = 0; i < dailyGoalList.size(); i++) {
            Integer day = dailyGoalList.get(i).getDay();
            LocalDate date = LocalDate.of(year, month, day);
            dailyGoalList.get(i).setDayOfWeek(date.getDayOfWeek().toString());
            dailyGoalList.get(i).setHasGoal(true);
        }
    }
}
