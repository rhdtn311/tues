package kong.tues.member.application.dto;

import kong.tues.member.domain.Member;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MemberJoinReqDto{

    @NotNull
    @NotEmpty
    @Length(max = 15)
    private String loginId;

    @NotNull
    @NotEmpty
    @Length(max = 15)
    private String password;

    @NotNull
    @NotEmpty
    @Length(max = 15)
    private String verifyPassword;

    @NotNull
    @NotEmpty
    @Email
    private String mail;

    public Member toMember() {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .mail(mail)
                .build();
    }
}
