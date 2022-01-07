package kong.tues.member.application;

import kong.tues.member.application.dto.MemberJoinReqDto;
import kong.tues.member.domain.Member;
import kong.tues.member.domain.repository.MemberRepository;
import kong.tues.member.exception.LoginIdExistException;
import kong.tues.member.exception.MailExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class JoinService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public void join(MemberJoinReqDto memberJoinReqDto) {

        Member member = memberJoinReqDto.toMember();
        if (memberRepository.existsMemberByLoginId(member.getLoginId())) {
            throw new LoginIdExistException(memberJoinReqDto);
        }

        if (memberRepository.existsMemberByMail(member.getMail())) {
            throw new MailExistException(memberJoinReqDto);
        }

        member.passwordEncode(passwordEncoder);
        memberRepository.save(member);
    }
}
