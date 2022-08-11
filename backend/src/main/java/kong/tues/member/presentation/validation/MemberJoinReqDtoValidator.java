package kong.tues.member.presentation.validation;

import kong.tues.member.application.dto.MemberJoinReqDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MemberJoinReqDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MemberJoinReqDto memberJoinReqDto = (MemberJoinReqDto) target;

        if (memberJoinReqDto.getPassword() != null && memberJoinReqDto.getVerifyPassword() != null) {
            if (!memberJoinReqDto.getPassword().equals(memberJoinReqDto.getVerifyPassword())) {
                errors.reject("passwordVerifyError", "비밀번호가 일치하지 않습니다.");
            }
        }
    }
}
