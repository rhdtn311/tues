package kong.tues.goal.dailyGoal.application.dto;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyGoalMainResDto {

    private Long id;
    private String name;
    private GoalType goalType;
    private AchieveType achieveType;
    private Integer goalCountQuota;
    private Integer goalCount;
    private Integer goalTimeQuota;
    private Integer goalTime;
    private Boolean success;
    private Object dayOfWeek;
}
