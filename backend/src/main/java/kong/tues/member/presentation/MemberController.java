package kong.tues.member.presentation;

import kong.tues.commons.dto.ResponseDTO;
import kong.tues.member.SessionConst;
import kong.tues.member.application.FindIdService;
import kong.tues.member.application.FindPasswordService;
import kong.tues.member.application.JoinService;
import kong.tues.member.application.LoginService;
import kong.tues.member.application.dto.MemberJoinReqDto;
import kong.tues.member.application.dto.MemberLoginReqDto;
import kong.tues.member.domain.Member;
import kong.tues.member.presentation.validation.MemberJoinReqDtoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/home")
@Controller
public class MemberController {

    private final JoinService joinService;
    private final LoginService loginService;
    private final FindIdService findIdService;
    private final FindPasswordService findPasswordService;

    private final MemberJoinReqDtoValidator memberJoinReqDtoValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(memberJoinReqDtoValidator);
    }

    @GetMapping()
    public String home(HttpServletRequest request, Model model) {

        log.info("GET : /member/home");
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "/member/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null) {
            return "/member/login";
        }

        model.addAttribute("member", loginMember);
        return "redirect:/goal/main";
    }

    @PostMapping("/join")
    public ResponseEntity<ResponseDTO> join(@RequestBody @Valid MemberJoinReqDto memberJoinReqDto) {

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(joinService.join(memberJoinReqDto))
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody MemberLoginReqDto memberLoginReqDto,
                                             HttpServletRequest request, HttpServletResponse response) throws IOException {

        Member member = loginService.login(memberLoginReqDto);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(member.getLoginId())
                .build());
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/member/home";
    }

    @GetMapping("/find")
    public String find(Model model) {
        model.addAttribute("loginId", null);
        model.addAttribute("mail", null);
        model.addAttribute("error", null);
        return "/member/find";
    }

    @GetMapping("/find/id")
    public String findId(@RequestParam("mail") String mail, Model model) {
        String loginId = findIdService.findLoginId(mail);

        model.addAttribute("loginId",loginId);

        return "/member/find";
    }

    @GetMapping("/find/password")
    public String findPassword(@RequestParam("loginId") String loginId,
                               @RequestParam("mail") String mail,
                               Model model) {

        findPasswordService.findPassword(loginId, mail);

        model.addAttribute("mail", mail);

        return "/member/find";
    }
}
