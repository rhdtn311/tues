package kong.tues.goal.mothlyGoal.presentation.validator;

import kong.tues.goal.AchieveType;
import kong.tues.goal.GoalType;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalReqDto;
import kong.tues.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MonthlyGoalReqDtoValidatorTest {

    @Autowired
    MonthlyGoalReqDtoValidator validator;

    public Errors errors;

    @Test
    @DisplayName("MonthlyGoalReqDto의 AchieveType이 COUNT인 경우 검증")
    void monthlyGoalAchieveTypeCountValidate() {
        // given
        MonthlyGoalReqDto monthlyGoalReqDto = MonthlyGoalReqDto.builder()
                .achieveType(AchieveType.COUNT)
                .name("수학문제 10문제 풀기")
                .goalCountQuota(null)
                .year(2022)
                .month(1)
                .goalType(GoalType.A)
                .content("시험 대비를 하기 위해 수학 문제를 푼다.")
                .build();

        // when
        errors = new BeanPropertyBindingResult(monthlyGoalReqDto, "monthlyGoalReqDto");
        validator.validate(monthlyGoalReqDto, errors);

        // then
        assertTrue(errors.hasErrors());
    }

    @Test
    @DisplayName("MonthlyGoalReqDto의 AchieveType이 TIME인 경우 검증")
    void monthlyGoalAchieveTypeTimeValidate() {
        // given
        MonthlyGoalReqDto monthlyGoalReqDto = MonthlyGoalReqDto.builder()
                .achieveType(AchieveType.TIME)
                .name("인터넷 강의 3시간 듣기")
                .goalTimeQuota(null)
                .year(2022)
                .month(1)
                .goalType(GoalType.A)
                .content("시험 대비를 하기 위해 인터넷 강의를 듣는다.")
                .build();

        // when
        errors = new BeanPropertyBindingResult(monthlyGoalReqDto, "monthlyGoalReqDto");
        validator.validate(monthlyGoalReqDto, errors);

        // then
        assertTrue(errors.hasErrors());
    }

    @Test
    @DisplayName("MonthlyGoalReqDto의 AchieveType이 BASIC인 경우 검증")
    void monthlyGoalAchieveTypeBasicValidate() {
        // given
        MonthlyGoalReqDto monthlyGoalReqDto = MonthlyGoalReqDto.builder()
                .achieveType(AchieveType.BASIC)
                .name("산책하기")
                .year(2022)
                .month(1)
                .goalType(GoalType.A)
                .content("컨디션 회복을 위해 산책을한다.")
                .build();

        // when
        errors = new BeanPropertyBindingResult(monthlyGoalReqDto, "monthlyGoalReqDto");
        validator.validate(monthlyGoalReqDto, errors);

        // then
        assertFalse(errors.hasErrors());
    }
}