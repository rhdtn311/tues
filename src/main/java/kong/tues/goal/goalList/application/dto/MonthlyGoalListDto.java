package kong.tues.goal.goalList.application.dto;

import kong.tues.goal.AchieveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class MonthlyGoalListDto {

    String goalName;
    Integer goalCountQuota;
    Integer goalCount;
    Integer goalTimeQuota;
    Integer goalTime;
    Boolean success;
    AchieveType achieveType;

    Integer achievementRate;

    public MonthlyGoalListDto(String goalName, Integer goalCountQuota, Integer goalCount, Integer goalTimeQuota, Integer goalTime, Boolean success, AchieveType achieveType) {
        this.goalName = goalName;
        this.goalCountQuota = goalCountQuota;
        this.goalCount = goalCount;
        this.goalTimeQuota = goalTimeQuota;
        this.goalTime = goalTime;
        this.success = success;
        this.achieveType = achieveType;

        if (achieveType == AchieveType.TIME) {
            achievementRate = (int) (((double)goalTime / goalTimeQuota) * 100);
        } else if (achieveType == AchieveType.COUNT) {
            achievementRate = (int) (((double)goalCount / goalCountQuota) * 100);
        } else {
            achievementRate = success ? 100 : 0;
        }
    }
}
