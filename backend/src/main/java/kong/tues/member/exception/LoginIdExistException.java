package kong.tues.member.exception;

import kong.tues.commons.exception.BusinessException;

public class LoginIdExistException extends BusinessException {
    public LoginIdExistException(Object object) {
        super(object);
    }
}
