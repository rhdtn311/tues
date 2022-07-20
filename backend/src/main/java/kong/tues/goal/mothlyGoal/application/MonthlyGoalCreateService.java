package kong.tues.goal.mothlyGoal.application;

import kong.tues.goal.GoalType;
import kong.tues.goal.exception.GoalNotFoundException;
import kong.tues.goal.exception.GoalTypeDupException;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalQueryRepository;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalRepository;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalReqDto;
import kong.tues.member.domain.Member;
import kong.tues.member.domain.repository.MemberRepository;
import kong.tues.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
    public List<String> findCreatedGoalTypes(Long monthlyGoalId) {

        MonthlyGoal monthlyGoal = monthlyGoalRepository.findById(monthlyGoalId).orElseThrow(GoalNotFoundException::new);

        List<GoalType> createdGoalTypes
                = monthlyGoalQueryRepository.findCreatedGoalTypes(monthlyGoal.getMember().getId(), monthlyGoal.getDate().getYear(), monthlyGoal.getDate().getMonthValue());

        return createdGoalTypes.stream().map(Enum::name).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> findCreatedGoalTypes(Long memberId, int year, int month) {

        List<GoalType> createdGoalTypes
                = monthlyGoalQueryRepository.findCreatedGoalTypes(memberId, year, month);


        return createdGoalTypes.stream().map(Enum::name).collect(Collectors.toList());
    }
}
