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

        log.info("joinService - 1");
        Member member = memberJoinReqDto.toMember();
        if (memberRepository.existsMemberByLoginId(member.getLoginId())) {
            log.info("joinService - 2");
            throw new LoginIdExistException(memberJoinReqDto);
        }

        log.info("joinService - 3");
        if (memberRepository.existsMemberByMail(member.getMail())) {
            log.info("joinService - 4");
            throw new MailExistException(memberJoinReqDto);
        }

        log.info("joinService - 5");
        member.passwordEncode(passwordEncoder);
        log.info("joinService - 6");
        memberRepository.save(member);
        log.info("joinService - 7");
    }
}
