package kong.tues.goal.dailyGoal.presentation.validator;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalReqDto;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalReqDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class DailyGoalReqDtoValidatorTest {

    @Autowired
    DailyGoalReqDtoValidator validator;

    public Errors errors;

    @Test
    @DisplayName("DailyGoalReqDto의 AchieveType이 WAKE인 경우 검증")
    void dailyGoalAchieveTypeWakeValidate() {

        // given
        DailyGoalReqDto dailyGoalReqDto = DailyGoalReqDto.builder()
                .achieveType(AchieveType.WAKE)
                .name("일찍 일어나기")
                .wakeUpHours(null)
                .wakeUpMinutes(null)
                .year(2022)
                .month(1)
                .goalType(GoalType.A)
                .content("8시에 기상한다.")
                .build();

        // when
        errors = new BeanPropertyBindingResult(dailyGoalReqDto, "dailyGoalReqDto");
        validator.validate(dailyGoalReqDto, errors);

        // then
        assertTrue(errors.hasErrors());
    }

    @Test
    @DisplayName("DailyGoalReqDto의 AchieveType이 COUNT인 경우 검증")
    void dailyGoalAchieveTypeCountValidate() {
        // given
        DailyGoalReqDto dailyGoalReqDto = DailyGoalReqDto.builder()
                .achieveType(AchieveType.COUNT)
                .name("수학문제 10문제 풀기")
                .goalCountQuota(null)
                .year(2022)
                .month(1)
                .goalType(GoalType.A)
                .content("시험 대비를 하기 위해 수학 문제를 푼다.")
                .build();

        // when
        errors = new BeanPropertyBindingResult(dailyGoalReqDto, "dailyGoalReqDto");
        validator.validate(dailyGoalReqDto, errors);

        // then
        assertTrue(errors.hasErrors());
    }

    @Test
    @DisplayName("DailyGoalReqDto의 AchieveType이 TIME인 경우 검증")
    void dailyGoalAchieveTypeTimeValidate() {
        // given
        DailyGoalReqDto dailyGoalReqDto = DailyGoalReqDto.builder()
                .achieveType(AchieveType.TIME)
                .name("인터넷 강의 3시간 듣기")
                .goalTimeQuota(null)
                .year(2022)
                .month(1)
                .goalType(GoalType.A)
                .content("시험 대비를 하기 위해 인터넷 강의를 듣는다.")
                .build();

        // when
        errors = new BeanPropertyBindingResult(dailyGoalReqDto, "dailyGoalReqDto");
        validator.validate(dailyGoalReqDto, errors);

        // then
        assertTrue(errors.hasErrors());
    }

    @Test
    @DisplayName("DailyGoalReqDto의 AchieveType이 BASIC인 경우 검증")
    void dailyGoalAchieveTypeBasicValidate() {
        // given
        DailyGoalReqDto dailyGoalReqDto = DailyGoalReqDto.builder()
                .achieveType(AchieveType.BASIC)
                .name("산책하기")
                .year(2022)
                .month(1)
                .goalType(GoalType.A)
                .content("컨디션 회복을 위해 산책을한다.")
                .build();

        // when
        errors = new BeanPropertyBindingResult(dailyGoalReqDto, "monthlyGoalReqDto");
        validator.validate(dailyGoalReqDto, errors);

        // then
        assertFalse(errors.hasErrors());
    }

}