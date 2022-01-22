package kong.tues.goal.exception;

import kong.tues.commons.exception.ExceptionDetails;
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

    @ExceptionHandler(GoalTimeOutOfRangeException.class)
    public String goalTimeOverExHandler(RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("error", ExceptionDetails.GOAL_TIME_OUT_OF_RANGE.getMessage());
        log.info("ERROR : GOAL_TIME_OVER_EXCEPTION = {}", ExceptionDetails.GOAL_TIME_OUT_OF_RANGE.getMessage());

        return "redirect:/goal/main";
    }

    @ExceptionHandler(GoalCountOutOfRangeException.class)
    public String goalCountOverExHandler(RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("error", ExceptionDetails.GOAL_COUNT_OUT_OF_RANGE.getMessage());
        log.info("ERROR : GOAL_COUNT_OVER_EXCEPTION = {}", ExceptionDetails.GOAL_COUNT_OUT_OF_RANGE.getMessage());

        return "redirect:/goal/main";
    }

}
