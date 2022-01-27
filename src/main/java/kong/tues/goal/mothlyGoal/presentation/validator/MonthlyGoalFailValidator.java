package kong.tues.goal.mothlyGoal.presentation.validator;

import kong.tues.goal.AchieveType;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalAchieveReqDto;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalAchieveResDto;
import org.springframework.stereotype.Component;

@Component
public class MonthlyGoalFailValidator {

    public boolean validate(Object object) {

        if (object instanceof MonthlyGoalAchieveReqDto) {
            MonthlyGoalAchieveReqDto monthlyGoalAchieveReqDto = (MonthlyGoalAchieveReqDto) object;

            if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.COUNT) {
                if (monthlyGoalAchieveReqDto.getValue() <= 0) {
                    return true;
                }
            } else if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.TIME) {
                if (monthlyGoalAchieveReqDto.getValue() <= 0) {
                    return true;
                }
            }
        } else if (object instanceof MonthlyGoalAchieveResDto) {
            MonthlyGoalAchieveResDto monthlyGoalAchieveResDto = (MonthlyGoalAchieveResDto) object;

            if (monthlyGoalAchieveResDto.getAchieveType() == AchieveType.COUNT) {
                if (monthlyGoalAchieveResDto.getGoalCount() <= 0) {
                    return true;
                }
            } else if (monthlyGoalAchieveResDto.getAchieveType() == AchieveType.TIME) {
                if (monthlyGoalAchieveResDto.getGoalTime() <= 0) {
                    return true;
                }
            }
        }
        return false;
    }

}
