package kong.tues.goal.dailyGoal.presentation.dto;

import kong.tues.goal.AchieveType;
import lombok.Data;

@Data
public class DailyGoalAchieveReqDto {

    private Long dailyGoalId;
    private AchieveType achieveType;
    private String week;
    private Integer value;
}
