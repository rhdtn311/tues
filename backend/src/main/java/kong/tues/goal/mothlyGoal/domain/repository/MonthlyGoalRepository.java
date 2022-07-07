package kong.tues.goal.mothlyGoal.domain.repository;

import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyGoalRepository extends JpaRepository<MonthlyGoal, Long> {
}
