package kong.tues.goal.dailyGoal.application;

import kong.tues.goal.dailyGoal.application.dto.CreatedMonthlyGoalResDto;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.domain.repository.DailyGoalRepository;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalReqDto;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalQueryRepository;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalRepository;
import kong.tues.member.domain.Member;
import kong.tues.member.domain.repository.MemberRepository;
import kong.tues.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class DailyGoalCreateService {

    private final DailyGoalRepository dailyGoalRepository;
    private final MemberRepository memberRepository;

    private final MonthlyGoalQueryRepository monthlyGoalQueryRepository;

    @Transactional
    public Long save(DailyGoalReqDto dailyGoalReqDto, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        MonthlyGoal findMonthlyGoal = findDailyGoalRelative(member, dailyGoalReqDto);

        return dailyGoalRepository.save(dailyGoalReqDto.toDailyGoal(member, findMonthlyGoal)).getId();
    }

    @Transactional(readOnly = true)
    public Map<String, String> findCreatedGoal(Long memberId, int year, int month) {

        return monthlyGoalsToMap(monthlyGoalQueryRepository.findCreatedMonthlyGoals(memberId, year, month));
    }

    private MonthlyGoal findDailyGoalRelative(Member member, DailyGoalReqDto dailyGoalReqDto) {

        return member.getMonthlyGoal().stream()
                .filter(monthlyGoal -> {
                    return monthlyGoal.getDate().getYear() == dailyGoalReqDto.getYear() &&
                            monthlyGoal.getDate().getMonthValue() == dailyGoalReqDto.getMonth() &&
                            monthlyGoal.getGoalType() == dailyGoalReqDto.getGoalType();
                }).findAny()
                .orElse(null);
    }

    public Map<String, String> monthlyGoalsToMap(List<CreatedMonthlyGoalResDto> createdGoalList) {
        Map<String, String> createdGoalsMap = new ConcurrentHashMap<>();

        for (CreatedMonthlyGoalResDto createdGoal : createdGoalList) {
            createdGoalsMap.put(createdGoal.getGoalType().toString(), createdGoal.getName());
        }

        return createdGoalsMap;
    }
}
