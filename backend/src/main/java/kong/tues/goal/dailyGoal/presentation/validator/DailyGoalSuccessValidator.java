package kong.tues.goal.dailyGoal.presentation.validator;

import kong.tues.goal.AchieveType;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalAchieveReqDto;
import org.springframework.stereotype.Component;

@Component
public class DailyGoalSuccessValidator {

    public boolean validate(Object object) {

        if (object instanceof DailyGoalAchieveReqDto) {
            DailyGoalAchieveReqDto dailyGoalAchieveReqDto = (DailyGoalAchieveReqDto) object;

            if (dailyGoalAchieveReqDto.getAchieveType() == AchieveType.COUNT) {
                if (dailyGoalAchieveReqDto.getValue() >= 100000000) {
                    return true;
                }
            } else if (dailyGoalAchieveReqDto.getAchieveType() == AchieveType.TIME) {
                if (dailyGoalAchieveReqDto.getValue() >= 24) {
                    return true;
                }
            }
        }
        return false;
    }
}
