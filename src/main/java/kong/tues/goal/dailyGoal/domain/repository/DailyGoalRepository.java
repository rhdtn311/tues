package kong.tues.goal.dailyGoal.domain.repository;

import kong.tues.goal.dailyGoal.domain.DailyGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyGoalRepository extends JpaRepository<DailyGoal, Long> {
}
