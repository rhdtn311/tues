package kong.tues.member.application;

import kong.tues.member.domain.repository.MemberRepository;
import kong.tues.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindIdService {

    private final MemberRepository memberRepository;

    public String findLoginId(String mail) {

        return memberRepository.findByMail(mail)
                .orElseThrow(MemberNotFoundException::new)
                .getLoginId();

    }
}
