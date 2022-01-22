package kong.tues.goal;

public interface GoalManager {

    public void successGoal(Long memberId, Long goalId);

    public void failGoal(Long memberId, Long goalId);

}
