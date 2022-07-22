package kong.tues.goal.dailyGoal.domain.repository;

import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyGoalRepository extends JpaRepository<DailyGoal, Long> {

    public List<DailyGoal> findDailyGoalByDateAndMember(LocalDate date, Member member);
}
