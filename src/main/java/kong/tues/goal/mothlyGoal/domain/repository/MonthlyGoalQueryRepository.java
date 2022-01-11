package kong.tues.goal.mothlyGoal.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static kong.tues.goal.mothlyGoal.domain.QMonthlyGoal.monthlyGoal;
import static kong.tues.member.domain.QMember.member;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MonthlyGoalQueryRepository {

    private final JPAQueryFactory queryFactory;

    public boolean existsMonthlyGoalByTypeAtMonth(MonthlyGoal monthlyGoalEntity) {
        return !queryFactory.selectFrom(monthlyGoal)
                .leftJoin(member).on(member.id.eq(monthlyGoalEntity.getMember().getId()))
                .where(eqMonth(monthlyGoalEntity).and(eqGoalType(monthlyGoalEntity)).and(eqYear(monthlyGoalEntity).and(eqMemberId(monthlyGoalEntity))))
                .fetch()
                .isEmpty();
    }

    public BooleanExpression eqYear(MonthlyGoal monthlyGoalEntity) {
        return monthlyGoal.date.year().eq(monthlyGoalEntity.getDate().getYear());
    }

    public BooleanExpression eqMonth(MonthlyGoal monthlyGoalEntity) {
        return monthlyGoal.date.month().eq(monthlyGoalEntity.getDate().getMonthValue());
    }

    public BooleanExpression eqGoalType(MonthlyGoal monthlyGoalEntity) {
        return monthlyGoal.goalType.eq(monthlyGoalEntity.getGoalType());
    }

    public BooleanExpression eqMemberId(MonthlyGoal monthlyGoalEntity) {
        return monthlyGoal.member.id.eq(monthlyGoalEntity.getMember().getId());
    }
}
