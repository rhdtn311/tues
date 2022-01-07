package kong.tues.commons.exception;

import kong.tues.member.exception.LoginFailException;
import kong.tues.member.exception.LoginIdExistException;
import kong.tues.member.exception.MailExistException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ExceptionDetails {

    NOT_FOUND_ERROR("0000", "식별할 수 없는 에러입니다.", NotFoundErrorException.class),

    LOGIN_ID_EXISTS("0001", "이미 존재하는 아이디입니다.", LoginIdExistException.class),
    MAIL_EXISTS("0002", "이미 존재하는 메일입니다.", MailExistException.class),
    LOGIN_FAIL("0003", "아이디 또는 비밀번호를 확인해주세요.", LoginFailException.class);

    private final String code;
    private final String message;
    private final Class<? extends Exception> clazz;

    public static ExceptionDetails findByClass(Class<? extends Exception> clazz) {
        return Arrays.stream(ExceptionDetails.values())
                .filter(exceptionDetail -> exceptionDetail.getClazz().equals(clazz))
                .findAny()
                .orElseThrow(NotFoundErrorException::new);
    }

}
