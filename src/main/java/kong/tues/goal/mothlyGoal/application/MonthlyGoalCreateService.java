package kong.tues.goal.mothlyGoal.application;

import kong.tues.goal.GoalType;
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

import java.util.ArrayList;
import java.util.List;

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
        if (monthlyGoalQueryRepository.existsMonthlyGoalByTypeAtMonth(monthlyGoal) && !(monthlyGoalReqDto.getGoalType() == GoalType.OTHER)){
            throw new GoalTypeDupException();
        }

        return monthlyGoalRepository.save(monthlyGoal).getId();
    }

    @Transactional(readOnly = true)
    public List<String> findCreatedGoalTypes(Long memberId, int year, int month) {
        // thymeleaf에서 단일 문자열은 list.contains가 동작하지 않기 때문에 리스트를 변형
        List<GoalType> createdGoalTypes
                = monthlyGoalQueryRepository.findCreatedGoalTypes(memberId, year, month);

        return getGoalTypes(createdGoalTypes);
    }

    // 타임리프에서 list.contains 메서드 사용 시 제대로 동작하지 않아 새로운 list 생성
    public List<String> getGoalTypes(List<GoalType> goalTypes) {
        // 65 ~ 74
        List<String> list = new ArrayList<>();
        for (int i = 65; i <= 74; i++) {
            char now = (char)i;
            if (goalTypes.contains(GoalType.valueOf(String.valueOf(now)))) {
                list.add(String.valueOf(now));
            } else {
                list.add("none");
            }
        }

        return list;
    }
}
