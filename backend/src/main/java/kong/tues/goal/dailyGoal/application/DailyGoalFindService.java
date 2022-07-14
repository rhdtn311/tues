package kong.tues.goal.dailyGoal.application;

import kong.tues.goal.dailyGoal.application.dto.DailyGoalMainResDto;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalQueryRepository;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DailyGoalFindService {

    private final DailyGoalQueryRepository dailyGoalQueryRepository;
    private final DailyGoalRepository dailyGoalRepository;

    public Map<String, List<DailyGoalMainResDto>> findWeeklyGoals(Long memberId, int year, int month, int day) {

        LocalDate date = LocalDate.of(year, month, day);

        int standardDay = date.getDayOfWeek().getValue();

        // 해당 월의 마지막 날
        int lastDayOfMonth = date.getMonth().length(isLeapYear(year));

        String[] days = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        Map<String, List<DailyGoalMainResDto>> weeklyGoalsMap = new ConcurrentHashMap<>();

        int Y = year;
        int M = month;
        int D = day - standardDay + 1;
        for (int i = 0; i < 7; i++) {

            // 해당 날짜의 DailyGoal을 가져옴
            List<DailyGoalMainResDto> weeklyGoals
                    = dailyGoalQueryRepository.findWeeklyGoals(memberId, Y, M, D++);
            weeklyGoalsMap.put(days[i], weeklyGoals);

            // 월의 말일이 지나 다음 월로 넘어가거나 다음 해로 넘어간 경우
            if (D > lastDayOfMonth) {
                M++;
                D = 1;

                if (M > 12) {
                    Y ++;
                    M = 1;
                }
            }
        }

        return weeklyGoalsMap;
    }

    public List<DailyGoalMainResDto> findDailyGoalsByDate(LocalDate date) {
        return dailyGoalRepository.findDailyGoalByDate(date)
                .stream()
                .map(DailyGoal::toDailyGoalMainResDTO)
                .collect(Collectors.toList());
    }

    // 윤년
    public boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }
}
