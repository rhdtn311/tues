package kong.tues.goal.dailyGoal.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kong.tues.goal.GoalType;
import kong.tues.goal.dailyGoal.application.dto.DailyGoalMainResDto;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.goalList.application.dto.DailyGoalListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static kong.tues.goal.dailyGoal.domain.QDailyGoal.dailyGoal;
import static kong.tues.member.domain.QMember.member;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DailyGoalQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<DailyGoalMainResDto> findWeeklyGoals(long memberId, int year, int month, int day) {

        return queryFactory.select(Projections.constructor(DailyGoalMainResDto.class,
                dailyGoal.id, dailyGoal.name, dailyGoal.goalType, dailyGoal.achieveType, dailyGoal.goalCountQuota,
                dailyGoal.goalCount, dailyGoal.goalTimeQuota, dailyGoal.goalTime, dailyGoal.success, dailyGoal.date))
                .from(dailyGoal)
                .leftJoin(member).on(member.id.eq(memberId))
                .where(eqYear(year).and(eqMonth(month)).and(eqDay(day)), eqMemberId(memberId))
                .fetch();
    }

    public List<DailyGoalListDto> findAllDailyGoals(long memberId, int year, int month) {

        return queryFactory.select(Projections.constructor(DailyGoalListDto.class,
                dailyGoal.name, dailyGoal.goalCountQuota, dailyGoal.goalCount, dailyGoal.goalTimeQuota,
                dailyGoal.goalTime, dailyGoal.success, dailyGoal.achieveType, dailyGoal.date.dayOfMonth()))
                .from(dailyGoal)
                .leftJoin(member).on(member.id.eq(memberId))
                .where(eqYear(year).and(eqMonth(month)).and(eqMemberId(memberId)))
                .fetch();
    }

    public List<DailyGoal> findDailyGoalsByYearAndMonth(long memberId, int year, int month, GoalType goalType) {

        return queryFactory.select(dailyGoal)
                .from(dailyGoal)
                .where(eqYear(year).and(eqMonth(month)).and(eqMemberId(memberId)).and(eqGoalType(goalType)))
                .fetch();
    }

    private BooleanExpression eqYear(int year) {
        return dailyGoal.date.year().eq(year);
    }

    private BooleanExpression eqMonth(int month) {
        return dailyGoal.date.month().eq(month);
    }

    private BooleanExpression eqDay(int day) {
        return dailyGoal.date.dayOfMonth().eq(day);
    }

    private BooleanExpression eqMemberId(long memberId) {
        return dailyGoal.member.id.eq(memberId);
    }

    private BooleanExpression eqGoalType(GoalType goalType) {return dailyGoal.goalType.eq(goalType);}
}
