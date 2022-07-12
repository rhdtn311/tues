package kong.tues.goal.presentation.dto;

import kong.tues.goal.dailyGoal.application.dto.DailyGoalMainResDto;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalMainResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@Data
public class MainResponse {

    private DateResponse dateResponse;
    private List<MonthlyGoalMainResDto> monthlyGoals = new ArrayList<>();
    private Map<String, List<DailyGoalMainResDto>> dailyGoals = new HashMap<>();

}
