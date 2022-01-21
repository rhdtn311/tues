package kong.tues.goal.mothlyGoal.application;

import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalQueryRepository;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalRepository;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalMainResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MonthlyGoalFindService {

    private final MonthlyGoalQueryRepository monthlyGoalQueryRepository;

    @Transactional
    public List<MonthlyGoalMainResDto> findMonthlyGoals(Long memberId, int year, int month) {

        return monthlyGoalQueryRepository.findAllMonthlyGoals(memberId, year, month);
    }
}
