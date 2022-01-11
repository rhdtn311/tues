package kong.tues.goal.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GoalExControllerAdvice {

    @ExceptionHandler(GoalTypeDupException.class)
    public String goalTypeDupExHandler() {
        return null;
    }
}
