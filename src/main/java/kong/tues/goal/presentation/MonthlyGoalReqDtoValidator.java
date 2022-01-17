package kong.tues.goal.presentation;

import kong.tues.goal.AchieveType;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalReqDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MonthlyGoalReqDtoValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MonthlyGoalReqDto monthlyGoalReqDto = (MonthlyGoalReqDto) target;

        if (monthlyGoalReqDto.getAchieveType() == AchieveType.WAKE) {
            if (monthlyGoalReqDto.getWakeUpHours() == null || monthlyGoalReqDto.getWakeUpMinutes() == null) {
                errors.reject("NoValue");
            }
        } else if (monthlyGoalReqDto.getAchieveType() == AchieveType.COUNT) {
            if (monthlyGoalReqDto.getGoalCountQuota() == null) {
                errors.reject("NoValue");
            }
        } else if (monthlyGoalReqDto.getAchieveType() == AchieveType.TIME) {
            if (monthlyGoalReqDto.getGoalTimeQuota() == null) {
                errors.reject("NoValue");
            }
        }
    }
}
