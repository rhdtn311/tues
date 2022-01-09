package kong.tues.goal.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/goal")
@RequiredArgsConstructor
@Controller
public class GoalController {

    @GetMapping("/main")
    public String goalMain() {
        return "/goal/main";
    }
}
