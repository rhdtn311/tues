package kong.tues.goal.mothlyGoal.application;

import kong.tues.goal.exception.GoalTypeDupException;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalQueryRepository;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalRepository;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalReqDto;
import kong.tues.member.domain.Member;
import kong.tues.member.domain.repository.MemberRepository;
import kong.tues.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MonthlyGoalCreateService {

    private final MemberRepository memberRepository;
    private final MonthlyGoalRepository monthlyGoalRepository;
    private final MonthlyGoalQueryRepository monthlyGoalQueryRepository;

    @Transactional
    public Long save(MonthlyGoalReqDto monthlyGoalReqDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        MonthlyGoal monthlyGoal = monthlyGoalReqDto.toMonthlyGoal(member);
        if (monthlyGoalQueryRepository.existsMonthlyGoalByTypeAtMonth(monthlyGoal)) {
            throw new GoalTypeDupException();
        }

        return monthlyGoalRepository.save(monthlyGoal).getId();
    }
}
