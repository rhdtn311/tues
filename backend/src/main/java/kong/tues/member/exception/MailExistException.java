package kong.tues.member.exception;

import kong.tues.commons.exception.BusinessException;
import kong.tues.member.application.dto.MemberJoinReqDto;

public class MailExistException extends BusinessException {

    public MailExistException (Object object) {
        super(object);
    }
}
