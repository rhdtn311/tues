package kong.tues.commons.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class BusinessException extends RuntimeException {

    private final ExceptionDetails exceptionDetails = ExceptionDetails.findByClass(this.getClass());

    private String code;
    private String message;

    private int status;
    private Object object;

    public BusinessException() {
        this.code = exceptionDetails.getCode();
        this.message = exceptionDetails.getMessage();
        this.status = exceptionDetails.getStatus();
    }

    public BusinessException(Object object) {
        this.code = exceptionDetails.getCode();
        this.message = exceptionDetails.getMessage();
        this.status = exceptionDetails.getStatus();
        this.object = object;
    }
}
