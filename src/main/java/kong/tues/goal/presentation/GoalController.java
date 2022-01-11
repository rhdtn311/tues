package kong.tues.goal.presentation;

import kong.tues.goal.AchieveType;
import kong.tues.goal.mothlyGoal.application.MonthlyGoalCreateService;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalReqDto;
import kong.tues.member.SessionConst;
import kong.tues.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public String createMonthlyGoal(Model model) {
        model.addAttribute("achieveTypes", AchieveType.values());
        return "/goal/createMonthlyGoal";
    }

    @PostMapping("/create/monthly")
    public String createMonthlyGoal(@ModelAttribute @Validated MonthlyGoalReqDto monthlyGoalReqDto,
                                    HttpServletRequest request) {

        log.info("monthlyGoal : {} " , monthlyGoalReqDto);
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            return "member/login";
        }
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        log.info("member = {}" , member);
        monthlyGoalCreateService.save(monthlyGoalReqDto, member.getId());

        return "redirect:/goal/main";
    }
}
