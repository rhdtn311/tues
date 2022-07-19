package kong.tues.goal.dailyGoal.presentation.validator;

import kong.tues.goal.AchieveType;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalReqDto;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Slf4j
@Component
public class DailyGoalReqDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (!target.getClass().equals(DailyGoalReqDto.class)) {
            return;
        }

        DailyGoalReqDto dailyGoalReqDto = (DailyGoalReqDto) target;

        if (dailyGoalReqDto.getAchieveType() == AchieveType.COUNT) {
            if (dailyGoalReqDto.getGoalCountQuota() == null) {
                errors.reject("NoValue", "값을 입력해주세요.");
            }
        } else if (dailyGoalReqDto.getAchieveType() == AchieveType.TIME) {
            if (dailyGoalReqDto.getGoalTimeQuota() == null) {
                errors.reject("NoValue", "값을 입력해주세요.");
            }
        }

        if (!isCorrectDay(dailyGoalReqDto.getYear(), dailyGoalReqDto.getMonth(), dailyGoalReqDto.getDay())) {
            errors.reject("day", "해당 년, 월에 없는 날짜입니다.");
        }
    }

    private boolean isCorrectDay(int year, int month, int day) {

        int lastDay = LocalDate.of(year, month, 1).lengthOfMonth();

        if (day < 1 || day > lastDay) return false;
        return true;
    }
}
