package kong.tues.goal.dailyGoal.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kong.tues.goal.dailyGoal.application.dto.DailyGoalMainResDto;
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
                dailyGoal.goalCount, dailyGoal.goalTimeQuota, dailyGoal.goalTime, dailyGoal.wakeUpTime, dailyGoal.success))
                .from(dailyGoal)
                .leftJoin(member).on(member.id.eq(memberId))
                .where(eqYear(year).and(eqMonth(month)).and(eqDay(day)), eqMemberId(memberId))
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
}
