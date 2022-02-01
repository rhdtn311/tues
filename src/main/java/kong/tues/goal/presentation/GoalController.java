package kong.tues.goal.presentation;

import kong.tues.commons.argumentresolver.Login;
import kong.tues.goal.AchieveType;
import kong.tues.goal.dailyGoal.application.DailyGoalAchieveService;
import kong.tues.goal.dailyGoal.application.DailyGoalCreateService;
import kong.tues.goal.dailyGoal.application.DailyGoalDetailService;
import kong.tues.goal.dailyGoal.application.DailyGoalFindService;
import kong.tues.goal.dailyGoal.application.dto.DailyGoalDetailResDto;
import kong.tues.goal.dailyGoal.application.dto.DailyGoalMainResDto;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalAchieveReqDto;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalAchieveResDto;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalReqDto;
import kong.tues.goal.dailyGoal.presentation.validator.DailyGoalFailValidator;
import kong.tues.goal.dailyGoal.presentation.validator.DailyGoalReqDtoValidator;
import kong.tues.goal.dailyGoal.presentation.validator.DailyGoalSuccessValidator;
import kong.tues.goal.mothlyGoal.application.*;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.dto.*;
import kong.tues.goal.mothlyGoal.presentation.validator.MonthlyGoalFailValidator;
import kong.tues.goal.mothlyGoal.presentation.validator.MonthlyGoalReqDtoValidator;
import kong.tues.goal.mothlyGoal.presentation.validator.MonthlyGoalSuccessValidator;
import kong.tues.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/goal")
@RequiredArgsConstructor
@Controller
public class GoalController {

    private final MonthlyGoalCreateService monthlyGoalCreateService;
    private final DailyGoalCreateService dailyGoalCreateService;
    private final MonthlyGoalFindService monthlyGoalFindService;
    private final DailyGoalFindService dailyGoalFindService;
    private final DailyGoalAchieveService dailyGoalAchieveService;
    private final MonthlyGoalAchieveService monthlyGoalAchieveService;
    private final MonthlyGoalUpdateService monthlyGoalUpdateService;
    private final MonthlyGoalDetailService monthlyGoalDetailService;
    private final MonthlyGoalDeleteService monthlyGoalDeleteService;
    private final DailyGoalDetailService dailyGoalDetailService;

    private final MonthlyGoalReqDtoValidator memberJoinReqDtoValidator;
    private final DailyGoalReqDtoValidator dailyGoalReqDtoValidator;
    private final DailyGoalSuccessValidator dailyGoalSuccessValidator;
    private final DailyGoalFailValidator dailyGoalFailValidator;
    private final MonthlyGoalSuccessValidator monthlyGoalSuccessValidator;
    private final MonthlyGoalFailValidator monthlyGoalFailValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(memberJoinReqDtoValidator);
        dataBinder.addValidators(dailyGoalReqDtoValidator);
    }

    @GetMapping("/main")
    public String main(@Login Member member, Model model,
                       @RequestParam(value = "error") @Nullable String error,
                       @RequestParam(value = "isDaily", defaultValue = "false") Boolean isDaily) {

        if (member == null) {
            return "member/login";
        }

        //에러가 있을 경우
        model.addAttribute("error", error);

        // 월간 목표 생성, 일간 목표 생성을 위한 데이터 (모달 창)
        model.addAttribute("year", LocalDate.now().getYear());
        model.addAttribute("month", LocalDate.now().getMonthValue());
        model.addAttribute("day", LocalDate.now().getDayOfMonth());

        // 월간목표를 위한 데이터
        List<MonthlyGoalMainResDto> monthlyGoals
                = monthlyGoalFindService.findMonthlyGoals(member.getId(), LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        model.addAttribute("monthlyGoals", monthlyGoals);

        // 주간 목표를 위한 데이터
        Map<String, List<DailyGoalMainResDto>> dailyGoals = dailyGoalFindService.findWeeklyGoals(member.getId(), LocalDate.now().getYear(), LocalDate.now().getMonthValue(),
                LocalDate.now().getDayOfMonth());

        model.addAttribute("dailyGoals", dailyGoals);

        // ajax
        model.addAttribute("monthlyGoal", new MonthlyGoal());

        // isDaily
        if (isDaily) {
            model.addAttribute("dailyGoal", dailyGoals.get(LocalDate.now().getDayOfWeek().name().substring(0, 3)));
            log.info("dailyGoal = {}", dailyGoals.get(LocalDate.now().getDayOfWeek().name().substring(0, 3)));
            return "/goal/main-daily";
        }

        // dailymodal
        model.addAttribute("createdGoalsMap", new HashMap<>());

        return "goal/main";
    }

    @GetMapping("/create/monthly")
    public String createMonthlyGoal(@Login Member member, Model model, Integer year, Integer month) {

        model.addAttribute("achieveTypes", AchieveType.values());
        log.info("되냐 .. ? 1");

        if (member == null) {
            return "redirect:/member/home";
        }

        List<String> createdGoalTypes
                = monthlyGoalCreateService.findCreatedGoalTypes(member.getId(), year, month);

        model.addAttribute("monthlyGoalReqDto", new MonthlyGoalReqDto());
        model.addAttribute("createdGoalTypes", createdGoalTypes);
        model.addAttribute("year", year);
        model.addAttribute("month", month);

        return "/goal/createMonthlyGoal :: #create-monthly-goal";
    }

    @PostMapping("/create/monthly")
    public String createMonthlyGoal(@ModelAttribute @Validated MonthlyGoalReqDto monthlyGoalReqDto,
                                    BindingResult bindingResult,
                                    @Login Member member,
                                    Model model) {
        log.info("monthlyGoal 생성!");

        if (bindingResult.hasErrors()) {
            log.info("[POST] /create/monthly ERROR : {}", bindingResult.getAllErrors());

            model.addAttribute("year", monthlyGoalReqDto.getYear());
            model.addAttribute("month", monthlyGoalReqDto.getMonth());

            return "redirect:/goal/main";
        }

        if (member == null) {
            return "member/login";
        }

        monthlyGoalCreateService.save(monthlyGoalReqDto, member.getId());

        return "redirect:/goal/main";
    }

    @GetMapping("/create/daily")
    public String createDailyGoal(@Login Member member, Model model, Integer year, Integer month, Integer day) {

        if (member == null) {
            return "member/login";
        }

        Map<String, String> createdGoalsMap = dailyGoalCreateService.findCreatedGoal(member.getId(), year, month);
        model.addAttribute("createdGoalsMap", createdGoalsMap);

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("day", day);

        model.addAttribute("dailyGoalReqDto", new DailyGoalReqDto());

        return "/goal/createDailyGoal :: #create-daily-goal";
    }

    @PostMapping("/create/daily")
    public String createDailyGoal(@ModelAttribute @Validated DailyGoalReqDto dailyGoalReqDto,
                                  BindingResult bindingResult,
                                  @Login Member member,
                                  Model model) {

        if (member == null) {
            return "member/login";
        }

        if (bindingResult.hasErrors()) {
            log.info("[POST] /create/daily ERROR : {}", bindingResult.getAllErrors());

            model.addAttribute("year", dailyGoalReqDto.getYear());
            model.addAttribute("month", dailyGoalReqDto.getMonth());
            model.addAttribute("day", dailyGoalReqDto.getDay());

            return "redirect:/goal/main";
        }

        dailyGoalCreateService.save(dailyGoalReqDto, member.getId());

        return "redirect:/goal/main";
    }

    // 월간 목표 개수 추가
    @PostMapping("/ajax/plus/monthly")
    public String successMonthlyGoal(@Login Member member,
                                     @ModelAttribute @Validated MonthlyGoalAchieveReqDto monthlyGoalAchieveReqDto,
                                     Model model) {

        log.info("monthlyGoalId = {}", monthlyGoalAchieveReqDto.getMonthlyGoalId());
        if (member == null) {
            return "member/login";
        }

        // 입력 검증
        if (monthlyGoalSuccessValidator.validate(monthlyGoalAchieveReqDto)) {
            return "/goal/main :: #error";
        }

        MonthlyGoal monthlyGoal = dailyGoalAchieveService.findMonthlyGoalIdByGoalType(member.getId(), monthlyGoalAchieveReqDto.getGoalType());
        if (monthlyGoal == null || monthlyGoal.getAchieveType() != monthlyGoalAchieveReqDto.getAchieveType()) {
            return "/goal/main :: #hidden";
        }

        MonthlyGoalAchieveResDto monthlyGoalAchieveResDto;
        if (monthlyGoalAchieveReqDto.getMonthlyGoalId() == 0L) {
            monthlyGoalAchieveResDto = monthlyGoalAchieveService.achieveMonthlyGoal(member.getId(), monthlyGoal.getId());
        } else {
            monthlyGoalAchieveResDto
                    = monthlyGoalAchieveService.achieveMonthlyGoal(member.getId(), monthlyGoalAchieveReqDto.getMonthlyGoalId());
        }

        // 일간 목표 추가 시 월간 목표의 개수가 할달량을 넘었을 때 체크 이미지 변경
        if (isMonthlyGoalSuccess(monthlyGoalAchieveResDto)) {
            return "/goal/main :: #monthly-goal-success";
        }

        model.addAttribute("monthlyGoal", monthlyGoalAchieveResDto);

        if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.COUNT) {
            return "/goal/main :: .monthly-count";
        } else if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.TIME) {
            return "/goal/main :: .monthly-time";
        } else if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.WAKE) {
            return "/goal/main :: .monthly-wake";
        } else if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.BASIC) {
            return "/goal/main :: .monthly-basic";
        }

        return "/goal/main";
    }

    private boolean isMonthlyGoalSuccess(MonthlyGoalAchieveResDto monthlyGoalAchieveResDto) {
        if (monthlyGoalAchieveResDto.getAchieveType() == AchieveType.COUNT) {
            if (monthlyGoalAchieveResDto.getGoalCount().equals(monthlyGoalAchieveResDto.getGoalCountQuota())) {
                return true;
            }
        } else if (monthlyGoalAchieveResDto.getAchieveType() == AchieveType.TIME) {
            if (monthlyGoalAchieveResDto.getGoalTime().equals(monthlyGoalAchieveResDto.getGoalTimeQuota())) {
                return true;
            }
        } else if (monthlyGoalAchieveResDto.getAchieveType() == AchieveType.BASIC) {
            if (monthlyGoalAchieveResDto.getSuccess()) {
                return true;
            }
        }
        return false;
    }

    // 월간 목표 개수 감소
    @PostMapping("/ajax/minus/monthly")
    public String failMonthlyGoal2(@Login Member member,
                                   @ModelAttribute @Validated MonthlyGoalAchieveReqDto monthlyGoalAchieveReqDto,
                                   Model model) {

        if (member == null) {
            return "member/login";
        }

        // 입력 검증
        if (monthlyGoalFailValidator.validate(monthlyGoalAchieveReqDto)) {
            return "/goal/main :: #error";
        }

        MonthlyGoal monthlyGoal = dailyGoalAchieveService.findMonthlyGoalIdByGoalType(member.getId(), monthlyGoalAchieveReqDto.getGoalType());
        if (monthlyGoal == null || monthlyGoal.getAchieveType() != monthlyGoalAchieveReqDto.getAchieveType()) {
            return "/goal/main :: #hidden";
        }

        MonthlyGoalAchieveResDto monthlyGoalAchieveResDto;
        if (monthlyGoalAchieveReqDto.getMonthlyGoalId() == 0L) {
            monthlyGoalAchieveResDto = monthlyGoalAchieveService.failMonthlyGoal(member.getId(), monthlyGoal.getId());
        } else {
            monthlyGoalAchieveResDto
                    = monthlyGoalAchieveService.failMonthlyGoal(member.getId(), monthlyGoalAchieveReqDto.getMonthlyGoalId());
        }

        // 일간 목표 감소 시 월간 목표의 개수가 할달량 밑으로 떨어졌을 때 체크 이미지 변경
        if (isMonthlyGoalNotSuccess(monthlyGoalAchieveResDto)) {
            return "/goal/main :: #monthly-goal-success";
        }

        model.addAttribute("monthlyGoal", monthlyGoalAchieveResDto);

        if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.COUNT) {
            return "/goal/main :: .monthly-count";
        } else if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.TIME) {
            return "/goal/main :: .monthly-time";
        } else if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.WAKE) {
            return "/goal/main :: .monthly-wake";
        } else if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.BASIC) {
            return "/goal/main :: .monthly-basic";
        }

        return "/goal/main";
    }

    private boolean isMonthlyGoalNotSuccess(MonthlyGoalAchieveResDto monthlyGoalAchieveResDto) {
        if (monthlyGoalAchieveResDto.getAchieveType() == AchieveType.COUNT) {
            if (monthlyGoalAchieveResDto.getGoalCount().equals(monthlyGoalAchieveResDto.getGoalCountQuota() - 1)) {
                return true;
            }
        } else if (monthlyGoalAchieveResDto.getAchieveType() == AchieveType.TIME) {
            if (monthlyGoalAchieveResDto.getGoalTime().equals(monthlyGoalAchieveResDto.getGoalTimeQuota() - 1)) {
                return true;
            }
        } else if (monthlyGoalAchieveResDto.getAchieveType() == AchieveType.BASIC) {
            if (!monthlyGoalAchieveResDto.getSuccess()) {
                return true;
            }
        }
        return false;
    }


    // 일간 목표 개수 추가
    @PostMapping("/ajax/plus/daily")
    public String successDailyGoal2(@Login Member member,
                                    @ModelAttribute DailyGoalAchieveReqDto dailyGoalAchieveReqDto,
                                    Model model) {

        if (member == null) {
            return "member/login";
        }

        // 입력 검증
        if (dailyGoalSuccessValidator.validate(dailyGoalAchieveReqDto)) {
            return "/goal/main :: #error";
        }

        Map<String, Object> dailyGoalAndMonthlyGoalMap
                = dailyGoalAchieveService.achieveDailyGoal(member.getId(), dailyGoalAchieveReqDto.getDailyGoalId());

        DailyGoalAchieveResDto dailyGoalAchieveResDto = (DailyGoalAchieveResDto) dailyGoalAndMonthlyGoalMap.get("dailyGoal");
        model.addAttribute("goal", dailyGoalAchieveResDto);

        if (dailyGoalAndMonthlyGoalMap.containsKey("monthlyGoal")) {
            MonthlyGoalAchieveResDto monthlyGoalAchieveResDto = (MonthlyGoalAchieveResDto) dailyGoalAndMonthlyGoalMap.get("monthlyGoal");

            // 값 검증
            if (monthlyGoalSuccessValidator.validate(monthlyGoalAchieveResDto)) {
                return "/goal/main :: #error";
            }

            model.addAttribute("monthlyGoal", monthlyGoalAchieveResDto);

        } else {
            model.addAttribute("monthlyGoal", new MonthlyGoal());
        }

        if (dailyGoalAchieveReqDto.getAchieveType() == AchieveType.COUNT) {
            return "/goal/main :: .daily-count-" + dailyGoalAchieveReqDto.getWeek();
        } else if (dailyGoalAchieveReqDto.getAchieveType() == AchieveType.TIME) {
            return "/goal/main :: .daily-time-" + dailyGoalAchieveReqDto.getWeek();
        } else if (dailyGoalAchieveReqDto.getAchieveType() == AchieveType.WAKE) {
            return "/goal/main :: .daily-wake-" + dailyGoalAchieveReqDto.getWeek();
        } else if (dailyGoalAchieveReqDto.getAchieveType() == AchieveType.BASIC) {
            return "/goal/main :: .daily-basic-" + dailyGoalAchieveReqDto.getWeek();
        }

        return "/goal/main";
    }

    // 일간 목표 개수 감소
    @PostMapping("/ajax/minus/daily")
    public String failDailyGoal2(@Login Member member,
                                 @ModelAttribute DailyGoalAchieveReqDto dailyGoalAchieveReqDto,
                                 Model model) {

        if (member == null) {
            return "member/login";
        }

        if (dailyGoalFailValidator.validate(dailyGoalAchieveReqDto)) {
            return "/goal/main :: #error";
        }

        Map<String, Object> dailyGoalAndMonthlyGoalMap
                = dailyGoalAchieveService.failDailyGoal(member.getId(), dailyGoalAchieveReqDto.getDailyGoalId());

        DailyGoalAchieveResDto dailyGoalAchieveResDto = (DailyGoalAchieveResDto) dailyGoalAndMonthlyGoalMap.get("dailyGoal");
        model.addAttribute("goal", dailyGoalAchieveResDto);

        if (dailyGoalAndMonthlyGoalMap.containsKey("monthlyGoal")) {
            MonthlyGoalAchieveResDto monthlyGoalAchieveResDto = (MonthlyGoalAchieveResDto) dailyGoalAndMonthlyGoalMap.get("monthlyGoal");
            // 값 검증
            if (monthlyGoalFailValidator.validate(monthlyGoalAchieveResDto)) {
                return "/goal/main :: #error";
            }

            model.addAttribute("monthlyGoal", monthlyGoalAchieveResDto);
        } else {
            model.addAttribute("monthlyGoal", new MonthlyGoal());
        }

        if (dailyGoalAchieveReqDto.getAchieveType() == AchieveType.COUNT) {
            return "/goal/main :: .daily-count-" + dailyGoalAchieveReqDto.getWeek();
        } else if (dailyGoalAchieveReqDto.getAchieveType() == AchieveType.TIME) {
            return "/goal/main :: .daily-time-" + dailyGoalAchieveReqDto.getWeek();
        } else if (dailyGoalAchieveReqDto.getAchieveType() == AchieveType.WAKE) {
            return "/goal/main :: .daily-wake-" + dailyGoalAchieveReqDto.getWeek();
        } else if (dailyGoalAchieveReqDto.getAchieveType() == AchieveType.BASIC) {
            return "/goal/main :: .daily-basic-" + dailyGoalAchieveReqDto.getWeek();
        }

        return "/goal/main";
    }

    @GetMapping("/update/monthly")
    public String getUpdateMonthlyGoal(@Login Member member,
                                       MonthlyGoalUpdateReqDto monthlyGoalUpdateReqDto,
                                       Model model) {

        log.info("update Monthly = {}", monthlyGoalUpdateReqDto.getMonthlyGoalId());
        if (member == null) {
            return "member/login";
        }
        List<String> createdGoalTypes
                = monthlyGoalCreateService.findCreatedGoalTypes(member.getId(), monthlyGoalUpdateReqDto.getYear(), monthlyGoalUpdateReqDto.getMonth());

        model.addAttribute("monthlyGoalReqDto", new MonthlyGoalReqDto());
        model.addAttribute("createdGoalTypes", createdGoalTypes);
        model.addAttribute("year", monthlyGoalUpdateReqDto.getYear());
        model.addAttribute("month", monthlyGoalUpdateReqDto.getMonth());

        MonthlyGoalUpdateResDto updateMonthlyGoal
                = monthlyGoalUpdateService.getMonthlyGoalUpdate(monthlyGoalUpdateReqDto.getMonthlyGoalId());

        model.addAttribute("updateMonthlyGoal", updateMonthlyGoal);

        return "/goal/updateMonthlyGoal :: #update-monthly-goal";
    }

    @PostMapping("/update/monthly")
    public String updateMonthlyGoal(@Login Member member,
                                    @ModelAttribute @Validated MonthlyGoalReqDto monthlyGoalReqDto,
                                    BindingResult bindingResult) {
        if (member == null) {
            return "member/login";
        }

        if (bindingResult.hasErrors()) {
            log.info("[POST] /update/monthly ERROR : {}", bindingResult.getAllErrors());

            return "redirect:/goal/main";
        }

        monthlyGoalUpdateService.updateMonthlyGoal(monthlyGoalReqDto, member);

        return "redirect:/goal/main";
    }

    @GetMapping("/detail/monthly")
    public String detailMonthly(@Login Member member,
                                Long monthlyGoalId,
                                Model model) {
        if (member == null) {
            return "/member/login";
        }

        MonthlyGoalDetailResDto detailGoal
                = monthlyGoalDetailService.getMonthlyGoalDetail(monthlyGoalId);


        model.addAttribute("detailGoal", detailGoal);

        model.addAttribute("year", LocalDate.now().getYear());
        model.addAttribute("month", LocalDate.now().getMonthValue());

        return "/goal/detailMonthlyGoal :: #monthly-goal-detail";
    }

    @PostMapping("/delete/monthly")
    public String deleteMonthly(@Login Member member, Long monthlyGoalId) {

        if (member == null) {
            return "/member/login";
        }

        monthlyGoalDeleteService.deleteMonthlyGoal(monthlyGoalId);

        return "redirect:/goal/main";
    }

    @GetMapping("/detail/daily")
    public String detailDaily(@Login Member member,
                              Long dailyGoalId,
                              Model model) {
        if (member == null) {
            return "/member/login";
        }

        DailyGoalDetailResDto detailGoal
                = dailyGoalDetailService.getDailyGoalDetail(dailyGoalId);
        log.info("detailDailyGoal = {}" , detailGoal);

        model.addAttribute("detailDailyGoal", detailGoal);

        model.addAttribute("year", LocalDate.now().getYear());
        model.addAttribute("month", LocalDate.now().getMonthValue());

        return "/goal/detailDailyGoal :: #daily-goal-detail";
    }
}
