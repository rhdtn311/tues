package kong.tues.member.application.dto;

import kong.tues.member.domain.Member;
import lombok.Data;

@Data
public class MemberLoginReqDto {

    private String loginId;
    private String password;
}
