package kong.tues.member.domain.repository;

import kong.tues.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByLoginId(String loginId);

    boolean existsMemberByMail(String mail);

    Optional<Member> findByLoginId(String LoginId);

    Optional<Member> findByMail(String mail);

    Optional<Member> findByLoginIdAndMail(String loginId, String mail);
}
