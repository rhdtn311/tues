package kong.tues.member.application;

import kong.tues.mail.application.MailSendService;
import kong.tues.mail.dto.FindPasswordMailDto;
import kong.tues.member.domain.Member;
import kong.tues.member.domain.repository.MemberRepository;
import kong.tues.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FindPasswordService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailSendService mailSendService;

    @Transactional
    public void findPassword(String loginId, String mail) {
        Member member = memberRepository.findByLoginIdAndMail(loginId, mail)
                .orElseThrow(MemberNotFoundException::new);

        String tempPassword = makeTempPassword();
        member.changePassword(tempPassword);
        member.passwordEncode(passwordEncoder);

        mailSendService.mailSend(new FindPasswordMailDto(mail, tempPassword));
    }

    // 임시 비밀번호 생성
    public String makeTempPassword() {
        return RandomString.make(7);
    }
}
