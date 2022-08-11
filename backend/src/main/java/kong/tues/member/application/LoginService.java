package kong.tues.member.application;

import kong.tues.member.application.dto.MemberLoginReqDto;
import kong.tues.member.domain.Member;
import kong.tues.member.domain.repository.MemberRepository;
import kong.tues.member.exception.LoginFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public Member login(MemberLoginReqDto memberLoginReqDto) {

        return memberRepository.findByLoginId(memberLoginReqDto.getLoginId())
                .filter(member -> passwordEncoder.matches(memberLoginReqDto.getPassword(), member.getPassword()))
                .orElseThrow(LoginFailException::new);
    }
}
