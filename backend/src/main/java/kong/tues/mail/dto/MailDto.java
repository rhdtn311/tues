package kong.tues.mail.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MailDto {

    private String address;
    private String title;
    private String message;

    public MailDto(String address) {
        this.address = address;
    }
}
