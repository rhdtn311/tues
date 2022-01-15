package kong.tues.goal.presentation;

import kong.tues.commons.argumentresolver.Login;
import kong.tues.goal.AchieveType;
import kong.tues.goal.mothlyGoal.application.MonthlyGoalCreateService;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalReqDto;
import kong.tues.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequestMapping("/goal")
@RequiredArgsConstructor
@Controller
public class GoalController {

    private final MonthlyGoalCreateService monthlyGoalCreateService;

    @GetMapping("/main")
    public String goalMain() {
        return "/goal/main";
    }

    @GetMapping("/create/monthly")
    public String createMonthlyGoal(Model model, @Login Member member) {
        model.addAttribute("achieveTypes", AchieveType.values());

        if (member == null) {
            return "member/login";
        }

        List<String> createdGoalTypes
                = monthlyGoalCreateService.findCreatedGoalTypes(member.getId(), LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        model.addAttribute("createdGoalTypes", createdGoalTypes);

        return "/goal/createMonthlyGoal";
    }

    @PostMapping("/create/monthly")
    public String createMonthlyGoal(@ModelAttribute @Validated MonthlyGoalReqDto monthlyGoalReqDto,
                                    @Login Member member) {

        if (member == null) {
            return "member/login";
        }

        monthlyGoalCreateService.save(monthlyGoalReqDto, member.getId());

        return "redirect:/goal/main";
    }
}
