package kong.tues.member.presentation;

import kong.tues.member.SessionConst;
import kong.tues.member.application.JoinService;
import kong.tues.member.application.LoginService;
import kong.tues.member.application.dto.MemberJoinReqDto;
import kong.tues.member.application.dto.MemberLoginReqDto;
import kong.tues.member.domain.Member;
import kong.tues.member.presentation.validation.MemberJoinReqDtoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final JoinService joinService;
    private final LoginService loginService;

    private final MemberJoinReqDtoValidator memberJoinReqDtoValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(memberJoinReqDtoValidator);
    }

    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "/member/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null) {
            return "/member/login";
        }

        model.addAttribute("member", loginMember);
        return "/goal/main";
    }

    @GetMapping("/join")
    public String join(Model model) {

        model.addAttribute("member", new MemberJoinReqDto());
        model.addAttribute("errorMember", new MemberJoinReqDto());
        return "member/join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute(value = "member") @Validated MemberJoinReqDto memberJoinReqDto,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/member/join";
        }

        joinService.join(memberJoinReqDto);

        return "redirect:/member/home";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberLoginReqDto memberLoginReqDto,
                        HttpServletRequest request) {

        Member member = loginService.login(memberLoginReqDto);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        return "redirect:/member/home";
    }

    @PostMapping("logout")
    public String logOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/member/home";
    }
}
