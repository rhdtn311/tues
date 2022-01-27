package kong.tues.goal.mothlyGoal.presentation.validator;

import kong.tues.goal.AchieveType;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalAchieveReqDto;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalAchieveReqDto;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalAchieveResDto;
import org.springframework.stereotype.Component;

@Component
public class MonthlyGoalSuccessValidator {

    public boolean validate(Object object) {


        if (object instanceof MonthlyGoalAchieveReqDto) {
            MonthlyGoalAchieveReqDto monthlyGoalAchieveReqDto = (MonthlyGoalAchieveReqDto) object;

            if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.COUNT) {
                if (monthlyGoalAchieveReqDto.getValue() >= 100000000) {
                    return true;
                }
            } else if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.TIME) {
                if (monthlyGoalAchieveReqDto.getValue() >= 1000) {
                    return true;
                }
            }
        } else if (object instanceof MonthlyGoalAchieveResDto) {
            MonthlyGoalAchieveResDto monthlyGoalAchieveResDto = (MonthlyGoalAchieveResDto) object;

            if (monthlyGoalAchieveResDto.getAchieveType() == AchieveType.COUNT) {
                if (monthlyGoalAchieveResDto.getGoalCount() >= 100000000) {
                    return true;
                }
            } else if (monthlyGoalAchieveResDto.getAchieveType() == AchieveType.TIME) {
                if (monthlyGoalAchieveResDto.getGoalTime() >= 1000) {
                    return true;
                }
            }
        }
        return false;
    }
}
