package kong.tues.commons.dto;

import kong.tues.commons.exception.BusinessException;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {

    private String message;
    private int status;

    public static ErrorResponse of(BusinessException e) {
        return new ErrorResponse(e.getMessage(), e.getStatus());
    }
}
