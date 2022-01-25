package kong.tues.goal;

public interface GoalManager {

    public Object successGoal(Long memberId, Long goalId);

    public Object failGoal(Long memberId, Long goalId);

}
