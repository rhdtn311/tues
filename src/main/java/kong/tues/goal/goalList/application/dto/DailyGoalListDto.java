package kong.tues.goal.goalList.application.dto;

import kong.tues.goal.AchieveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class DailyGoalListDto {

    String goalName;
    Integer goalCountQuota;
    Integer goalCount;
    Integer goalTimeQuota;
    Integer goalTime;
    Boolean success;
    AchieveType achieveType;
    Integer day;
    String dayOfWeek;
    boolean hasGoal;

    public DailyGoalListDto(String goalName, Integer goalCountQuota, Integer goalCount, Integer goalTimeQuota, Integer goalTime, Boolean success, AchieveType achieveType, Integer day) {
        this.goalName = goalName;
        this.goalCountQuota = goalCountQuota;
        this.goalCount = goalCount;
        this.goalTimeQuota = goalTimeQuota;
        this.goalTime = goalTime;
        this.success = success;
        this.achieveType = achieveType;
        this.day = day;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public Integer getGoalCountQuota() {
        return goalCountQuota;
    }

    public void setGoalCountQuota(Integer goalCountQuota) {
        this.goalCountQuota = goalCountQuota;
    }

    public Integer getGoalCount() {
        return goalCount;
    }

    public void setGoalCount(Integer goalCount) {
        this.goalCount = goalCount;
    }

    public Integer getGoalTimeQuota() {
        return goalTimeQuota;
    }

    public void setGoalTimeQuota(Integer goalTimeQuota) {
        this.goalTimeQuota = goalTimeQuota;
    }

    public Integer getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(Integer goalTime) {
        this.goalTime = goalTime;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public AchieveType getAchieveType() {
        return achieveType;
    }

    public void setAchieveType(AchieveType achieveType) {
        this.achieveType = achieveType;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public boolean isHasGoal() {
        return hasGoal;
    }

    public void setHasGoal(boolean hasGoal) {
        this.hasGoal = hasGoal;
    }
}
