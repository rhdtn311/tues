package kong.tues.member.presentation;

import kong.tues.member.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberValidationController {

    @PostMapping("/validate/member")
    public boolean validateMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            return false;
        }
        return true;
    }
}
