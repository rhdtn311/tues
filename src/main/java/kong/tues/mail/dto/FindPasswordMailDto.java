package kong.tues.mail.dto;

import lombok.Getter;

@Getter
public class FindPasswordMailDto extends MailDto {

    private String tempPassword;
    private String title;
    private String message;

    public FindPasswordMailDto(String address, String tempPassword) {
        super(address);
        this.tempPassword = tempPassword;
        this.title = "tues : 임시 비밀번호 입니다.";
        this.message = "임시 비밀번호는 : " + tempPassword + " 입니다.";
    }
}
