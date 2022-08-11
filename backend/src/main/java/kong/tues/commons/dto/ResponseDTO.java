package kong.tues.commons.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private Object data;
    private final int status = 200;

}
