package kong.tues.commons.exception;

import kong.tues.commons.dto.ErrorResponse;
import kong.tues.commons.dto.ValidationExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerAdaptor {

    // 비즈니스 예외 처리
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        return new ResponseEntity<>(ErrorResponse.of(e), HttpStatus.valueOf(e.getStatus()));
    }

    // 바인딩 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationExceptionResponseDTO>> handleValidationException(MethodArgumentNotValidException e) {
        List<ValidationExceptionResponseDTO> list = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError) list.add(new ValidationExceptionResponseDTO(((FieldError) error).getField(), error.getDefaultMessage()));
            else list.add(new ValidationExceptionResponseDTO(error.getCode(), error.getDefaultMessage()));
        });
        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }
}
