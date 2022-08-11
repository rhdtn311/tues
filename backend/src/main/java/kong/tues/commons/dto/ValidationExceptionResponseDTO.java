package kong.tues.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationExceptionResponseDTO {

    private String code;
    private String message;
}
