package kong.tues.goal.mothlyGoal.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.dailyGoal.application.dto.CreatedMonthlyGoalResDto;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalMainResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                .where(eqMonth(monthlyGoalEntity.getDate().getMonthValue())
                        .and(eqGoalType(monthlyGoalEntity.getGoalType()))
                        .and(eqYear(monthlyGoalEntity.getDate().getYear()))
                        .and(eqMemberId(monthlyGoalEntity.getMember().getId())))
                .fetch()
                .isEmpty();
    }

    public List<GoalType> findCreatedGoalTypes(Long memberId, int year, int month) {
        return queryFactory.select(monthlyGoal.goalType)
                .from(monthlyGoal)
                .leftJoin(member).on(member.id.eq(memberId))
                .where(eqYear(year).and(eqMonth(month)).and(eqMemberId(memberId)))
                .fetch();
    }

    public List<CreatedMonthlyGoalResDto> findCreatedMonthlyGoals(Long memberId, int year, int month) {
        return queryFactory.select(Projections.constructor(CreatedMonthlyGoalResDto.class,
                monthlyGoal.goalType, monthlyGoal.name))
                .from(monthlyGoal)
                .leftJoin(member).on(member.id.eq(memberId))
                .where(eqYear(year).and(eqMonth(month)).and(eqMemberId(memberId)))
                .fetch();
    }

    public List<MonthlyGoalMainResDto> findAllMonthlyGoals(Long memberId, int year, int month) {
        return queryFactory.select(Projections.constructor(MonthlyGoalMainResDto.class,
                monthlyGoal.id, monthlyGoal.name, monthlyGoal.goalType, monthlyGoal.achieveType,monthlyGoal.goalCountQuota, monthlyGoal.goalCount,
                monthlyGoal.goalTimeQuota, monthlyGoal.goalTime,  monthlyGoal.success))
                .from(monthlyGoal)
                .leftJoin(member).on(member.id.eq(memberId))
                .where(eqYear(year).and(eqMonth(month)).and(eqMemberId(memberId)))
                .fetch();
    }

    public MonthlyGoal findMonthlyGoalByGoalType(Long memberId, int year, int month, GoalType goalType) {
        return queryFactory.selectFrom(monthlyGoal)
                .leftJoin(member).on(member.id.eq(memberId))
                .where(eqYear(year).and(eqMonth(month)).and(eqGoalType(goalType)).and(eqMemberId(memberId)))
                .fetchOne();
    }

    public MonthlyGoal findMonthlyGoalByGoalTypeAndAchieveType(Long memberId, int year, int month, GoalType goalType, AchieveType achieveType) {
        return queryFactory.selectFrom(monthlyGoal)
                .leftJoin(member).on(member.id.eq(memberId))
                .where(eqYear(year).and(eqMonth(month)).and(eqGoalType(goalType)).and(eqMemberId(memberId)).and(eqAchieveType(achieveType)))
                .fetchOne();
    }

    public BooleanExpression eqYear(int year) {
        return monthlyGoal.date.year().eq(year);
    }

    public BooleanExpression eqMonth(int month) {
        return monthlyGoal.date.month().eq(month);
    }

    public BooleanExpression eqGoalType(GoalType goalType) {
        return monthlyGoal.goalType.eq(goalType);
    }

    public BooleanExpression eqMemberId(Long memberId) {
        return monthlyGoal.member.id.eq(memberId);
    }

    public BooleanExpression eqAchieveType(AchieveType achieveType) {
        return monthlyGoal.achieveType.eq(achieveType);
    }

}
