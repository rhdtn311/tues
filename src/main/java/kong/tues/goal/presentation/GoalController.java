package kong.tues.goal.presentation;

import kong.tues.commons.argumentresolver.Login;
import kong.tues.goal.AchieveType;
import kong.tues.goal.dailyGoal.application.DailyGoalAchieveService;
import kong.tues.goal.dailyGoal.application.DailyGoalCreateService;
import kong.tues.goal.dailyGoal.application.DailyGoalFindService;
import kong.tues.goal.dailyGoal.application.dto.DailyGoalMainResDto;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalAchieveReqDto;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalAchieveResDto;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalReqDto;
import kong.tues.goal.dailyGoal.presentation.validator.DailyGoalReqDtoValidator;
import kong.tues.goal.mothlyGoal.application.MonthlyGoalAchieveService;
import kong.tues.goal.mothlyGoal.application.MonthlyGoalCreateService;
import kong.tues.goal.mothlyGoal.application.MonthlyGoalFindService;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.domain.repository.MonthlyGoalRepository;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalAchieveResDto;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalMainResDto;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalReqDto;
import kong.tues.goal.mothlyGoal.dto.MonthlyGoalAchieveReqDto;
import kong.tues.goal.mothlyGoal.presentation.validator.MonthlyGoalReqDtoValidator;
import kong.tues.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
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

    private final MonthlyGoalReqDtoValidator memberJoinReqDtoValidator;
    private final DailyGoalReqDtoValidator dailyGoalReqDtoValidator;

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
            log.info("dailyGoal = {}" , dailyGoals.get(LocalDate.now().getDayOfWeek().name().substring(0, 3)));
            return "/goal/main-daily";
        }

        return "goal/main";
    }

    @GetMapping("/create/monthly")
    public String createMonthlyGoal(@Login Member member, Model model, Integer year, Integer month) {

        model.addAttribute("achieveTypes", AchieveType.values());

        if (member == null) {
            return "redirect:/member/home";
        }

        List<String> createdGoalTypes
                = monthlyGoalCreateService.findCreatedGoalTypes(member.getId(), year, month);

        model.addAttribute("monthlyGoalReqDto", new MonthlyGoalReqDto());
        model.addAttribute("createdGoalTypes", createdGoalTypes);
        model.addAttribute("year", year);
        model.addAttribute("month", month);


        return "/goal/createMonthlyGoal";
    }

    @PostMapping("/create/monthly")
    public String createMonthlyGoal(@ModelAttribute @Validated MonthlyGoalReqDto monthlyGoalReqDto,
                                    BindingResult bindingResult,
                                    @Login Member member,
                                    Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("year", monthlyGoalReqDto.getYear());
            model.addAttribute("month", monthlyGoalReqDto.getMonth());
            List<String> createdGoalTypes
                    = monthlyGoalCreateService.findCreatedGoalTypes(member.getId(), monthlyGoalReqDto.getYear(), monthlyGoalReqDto.getMonth());

            model.addAttribute("createdGoalTypes", createdGoalTypes);

            return "/goal/createMonthlyGoal";
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

        return "/goal/createDailyGoal";
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
            Map<String, String> createdGoalsMap = dailyGoalCreateService.findCreatedGoal(member.getId(), dailyGoalReqDto.getYear(), dailyGoalReqDto.getMonth());
            model.addAttribute("createdGoalsMap", createdGoalsMap);

            model.addAttribute("year", dailyGoalReqDto.getYear());
            model.addAttribute("month", dailyGoalReqDto.getMonth());
            model.addAttribute("day", dailyGoalReqDto.getDay());

            return "/goal/createDailyGoal";
        }

        dailyGoalCreateService.save(dailyGoalReqDto, member.getId());

        return "redirect:/goal/main";
    }

    // 월간 목표 개수 추가
    @PostMapping("/ajax/plus/monthly")
    public String successMonthlyGoal2(@Login Member member,
                                      @ModelAttribute @Validated MonthlyGoalAchieveReqDto monthlyGoalAchieveReqDto,
                                      Model model) {

        log.info("monthlyGoalId = {}" , monthlyGoalAchieveReqDto.getMonthlyGoalId());
        if (member == null) {
            return "member/login";
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

        model.addAttribute("monthlyGoal", monthlyGoalAchieveResDto);

        if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.COUNT) {
            log.info("monthly->count");
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

    // 월간 목표 개수 감소
    @PostMapping("/ajax/minus/monthly")
    public String failMonthlyGoal2(@Login Member member,
                                   @ModelAttribute @Validated MonthlyGoalAchieveReqDto monthlyGoalAchieveReqDto,
                                   Model model) {

        if (member == null) {
            return "member/login";
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

    // 일간 목표 개수 추가
    @PostMapping("/ajax/plus/daily")
    public String successDailyGoal2(@Login Member member,
                                    @ModelAttribute DailyGoalAchieveReqDto dailyGoalAchieveReqDto,
                                    Model model) {

        if (member == null) {
            return "member/login";
        }

        Map<String, Object> dailyGoalAndMonthlyGoalMap
                = dailyGoalAchieveService.achieveDailyGoal(member.getId(), dailyGoalAchieveReqDto.getDailyGoalId());

        DailyGoalAchieveResDto dailyGoalAchieveResDto = (DailyGoalAchieveResDto) dailyGoalAndMonthlyGoalMap.get("dailyGoal");
        model.addAttribute("goal", dailyGoalAchieveResDto);

        if (dailyGoalAndMonthlyGoalMap.containsKey("monthlyGoal")) {
            MonthlyGoalAchieveResDto monthlyGoalAchieveResDto = (MonthlyGoalAchieveResDto) dailyGoalAndMonthlyGoalMap.get("monthlyGoal");
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

        Map<String, Object> dailyGoalAndMonthlyGoalMap
                = dailyGoalAchieveService.failDailyGoal(member.getId(), dailyGoalAchieveReqDto.getDailyGoalId());

        DailyGoalAchieveResDto dailyGoalAchieveResDto = (DailyGoalAchieveResDto) dailyGoalAndMonthlyGoalMap.get("dailyGoal");
        model.addAttribute("goal", dailyGoalAchieveResDto);

        if (dailyGoalAndMonthlyGoalMap.containsKey("monthlyGoal")) {
            MonthlyGoalAchieveResDto monthlyGoalAchieveResDto = (MonthlyGoalAchieveResDto) dailyGoalAndMonthlyGoalMap.get("monthlyGoal");
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
}
