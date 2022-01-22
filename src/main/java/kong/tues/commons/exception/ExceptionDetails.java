package kong.tues.commons.exception;

import kong.tues.goal.exception.GoalCountOutOfRangeException;
import kong.tues.goal.exception.GoalNotFoundException;
import kong.tues.goal.exception.GoalTimeOutOfRangeException;
import kong.tues.goal.exception.GoalTypeDupException;
import kong.tues.member.exception.LoginFailException;
import kong.tues.member.exception.LoginIdExistException;
import kong.tues.member.exception.MailExistException;
import kong.tues.member.exception.MemberNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ExceptionDetails {

    NOT_FOUND_ERROR("0000", "식별할 수 없는 에러입니다.", NotFoundErrorException.class),

    LOGIN_ID_EXISTS("0001", "이미 존재하는 아이디입니다.", LoginIdExistException.class),
    MAIL_EXISTS("0002", "이미 존재하는 메일입니다.", MailExistException.class),
    LOGIN_FAIL("0003", "아이디 또는 비밀번호를 확인해주세요.", LoginFailException.class),
    MEMBER_NOT_FOUND("0004", "존재하지 않는 회원입니다.", MemberNotFoundException.class),

    GOAL_TYPE_DUPLICATION("0005", "같은 종류의 목표가 이미 존재합니다.", GoalTypeDupException.class),
    GOAL_NOT_FOUND("0006", "목표를 찾을 수 없습니다.", GoalNotFoundException.class),
    GOAL_TIME_OUT_OF_RANGE("0007", "지정된 시간 범위를 벗어납니다. (일간 : 24시간, 월간 1000시간)", GoalTimeOutOfRangeException.class),
    GOAL_COUNT_OUT_OF_RANGE("0008", "지정된 개수 범위를 벗어납니다. (일간, 월간 : 100000000개)", GoalCountOutOfRangeException.class);


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
