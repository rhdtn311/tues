package kong.tues.goal.mothlyGoal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyGoalUpdateReqDto {

    private Long monthlyGoalId;
    private Integer year;
    private Integer month;
}
