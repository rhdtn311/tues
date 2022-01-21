package kong.tues.goal.exception;

import kong.tues.commons.exception.ExceptionDetails;
import kong.tues.member.exception.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class GoalExControllerAdvice {

    @ExceptionHandler(GoalTypeDupException.class)
    public String goalTypeDupExHandler() {
        return null;
    }

    @ExceptionHandler(GoalTimeOverException.class)
    public String goalTimeOverExHandler(RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("error", ExceptionDetails.GOAL_TIME_OVER.getMessage());
        log.info("ERROR : GOAL_TIME_OVER_EXCEPTION = {}", ExceptionDetails.GOAL_TIME_OVER.getMessage());

        return "redirect:/goal/main";
    }

    @ExceptionHandler(GoalCountOverException.class)
    public String goalCountOverExHandler(RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("error", ExceptionDetails.GOAL_COUNT_OVER.getMessage());
        log.info("ERROR : GOAL_COUNT_OVER_EXCEPTION = {}", ExceptionDetails.GOAL_COUNT_OVER.getMessage());

        return "redirect:/goal/main";
    }

}
