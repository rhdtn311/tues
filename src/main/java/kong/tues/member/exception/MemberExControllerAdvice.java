package kong.tues.member.exception;

import kong.tues.member.application.dto.MemberJoinReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class MemberExControllerAdvice {

    @ExceptionHandler(LoginIdExistException.class)
    public String loginExHandler(LoginIdExistException error, Model model) {

        model.addAttribute("member", new MemberJoinReqDto());
        model.addAttribute("error", error.getMessage());

        MemberJoinReqDto errorMember = (MemberJoinReqDto) error.getObject();
        errorMember.setLoginId("");

        model.addAttribute("errorMember", errorMember);

        return "member/join";
    }

    @ExceptionHandler(MailExistException.class)
    public String mailExHandler(MailExistException error, Model model) {

        model.addAttribute("member", new MemberJoinReqDto());
        model.addAttribute("error", error.getMessage());

        MemberJoinReqDto errorMember = (MemberJoinReqDto) error.getObject();
        errorMember.setMail("");

        model.addAttribute("errorMember", errorMember);

        return "member/join";
    }

    @ExceptionHandler(LoginFailException.class)
    public String loginFail(LoginFailException error, Model model) {
        model.addAttribute("error", error.getMessage());

        return "member/login";
    }
}
