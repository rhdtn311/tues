package kong.tues.goal.presentation;

import kong.tues.commons.argumentresolver.Login;
import kong.tues.goal.AchieveType;
import kong.tues.goal.dailyGoal.application.*;
import kong.tues.goal.dailyGoal.application.dto.DailyGoalDetailResDto;
import kong.tues.goal.dailyGoal.application.dto.DailyGoalMainResDto;
import kong.tues.goal.dailyGoal.application.dto.DailyGoalUpdateResDto;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalAchieveReqDto;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalAchieveResDto;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalReqDto;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalUpdateReqDto;
import kong.tues.goal.dailyGoal.presentation.validator.DailyGoalFailValidator;
import kong.tues.goal.dailyGoal.presentation.validator.DailyGoalReqDtoValidator;
import kong.tues.goal.dailyGoal.presentation.validator.DailyGoalSuccessValidator;
import kong.tues.goal.goalList.application.GoalListService;
import kong.tues.goal.goalList.application.dto.DailyGoalListDto;
import kong.tues.goal.goalList.application.dto.MonthlyGoalListDto;
import kong.tues.goal.mothlyGoal.application.*;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.dto.*;
import kong.tues.goal.mothlyGoal.presentation.validator.MonthlyGoalFailValidator;
import kong.tues.goal.mothlyGoal.presentation.validator.MonthlyGoalReqDtoValidator;
import kong.tues.goal.mothlyGoal.presentation.validator.MonthlyGoalSuccessValidator;
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

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.IntStream;

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
    private final DailyGoalUpdateService dailyGoalUpdateService;
    private final DailyGoalDeleteService dailyGoalDeleteService;
    private final GoalListService goalListService;

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
                       @RequestParam(value = "isDaily", defaultValue = "false") Boolean isDaily,
                       HttpSession httpSession) {

        log.info("Session timeout = {}", httpSession.getMaxInactiveInterval());

        if (member == null) {
            return "/member/login";
        }

        //????????? ?????? ??????
        model.addAttribute("error", error);

        // ?????? ?????? ??????, ?????? ?????? ????????? ?????? ????????? (?????? ???)
        model.addAttribute("year", LocalDate.now().getYear());
        model.addAttribute("month", LocalDate.now().getMonthValue());
        model.addAttribute("day", LocalDate.now().getDayOfMonth());
        model.addAttribute("dayOfWeek", LocalDate.now().getDayOfWeek().name());

        // ??????????????? ?????? ?????????
        List<MonthlyGoalMainResDto> monthlyGoals
                = monthlyGoalFindService.findMonthlyGoals(member.getId(), LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        model.addAttribute("monthlyGoals", monthlyGoals);

        // ?????? ????????? ?????? ?????????
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

        return "/goal/main";
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

        return "/goal/createMonthlyGoal :: #create-monthly-goal-modal";
    }

    @PostMapping("/create/monthly")
    public String createMonthlyGoal(@ModelAttribute @Validated MonthlyGoalReqDto monthlyGoalReqDto,
                                    BindingResult bindingResult,
                                    @Login Member member,
                                    Model model) {
        log.info("monthlyGoal ??????!");

        if (bindingResult.hasErrors()) {
            log.info("[POST] /create/monthly ERROR : {}", bindingResult.getAllErrors());

            model.addAttribute("year", monthlyGoalReqDto.getYear());
            model.addAttribute("month", monthlyGoalReqDto.getMonth());

            return "redirect:/goal/main";
        }

        if (member == null) {
            return "/member/login";
        }

        monthlyGoalCreateService.save(monthlyGoalReqDto, member.getId());

        return "redirect:/goal/main";
    }

    @GetMapping("/create/daily")
    public String createDailyGoal(@Login Member member, Model model, Integer year, Integer month, Integer day) {

        if (member == null) {
            return "/member/login";
        }

        Map<String, String> createdGoalsMap = dailyGoalCreateService.findCreatedGoal(member.getId(), year, month);
        model.addAttribute("createdGoalsMap", createdGoalsMap);

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("day", day);

        model.addAttribute("dailyGoalReqDto", new DailyGoalReqDto());

        return "/goal/createDailyGoal :: #create-daily-goal-modal";
    }

    @PostMapping("/create/daily")
    public String createDailyGoal(@ModelAttribute @Validated DailyGoalReqDto dailyGoalReqDto,
                                  BindingResult bindingResult,
                                  @Login Member member,
                                  Model model) {

        if (member == null) {
            return "/member/login";
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

    // ?????? ?????? ?????? ??????
    @PostMapping("/ajax/plus/monthly")
    public String successMonthlyGoal(@Login Member member,
                                     @ModelAttribute @Validated MonthlyGoalAchieveReqDto monthlyGoalAchieveReqDto,
                                     Model model) {

        log.info("monthlyGoalId = {}", monthlyGoalAchieveReqDto.getMonthlyGoalId());
        if (member == null) {
            return "/member/login";
        }

        // ?????? ??????
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

        // ?????? ?????? ?????? ??? ?????? ????????? ????????? ???????????? ????????? ??? ?????? ????????? ??????
        if (isMonthlyGoalSuccess(monthlyGoalAchieveResDto)) {
            return "/goal/main :: #monthly-goal-success";
        }

        model.addAttribute("monthlyGoal", monthlyGoalAchieveResDto);

        if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.COUNT) {
            return "/goal/main :: .monthly-count";
        } else if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.TIME) {
            return "/goal/main :: .monthly-time";
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

    // ?????? ?????? ?????? ??????
    @PostMapping("/ajax/minus/monthly")
    public String failMonthlyGoal2(@Login Member member,
                                   @ModelAttribute @Validated MonthlyGoalAchieveReqDto monthlyGoalAchieveReqDto,
                                   Model model) {

        if (member == null) {
            return "/member/login";
        }

        // ?????? ??????
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

        // ?????? ?????? ?????? ??? ?????? ????????? ????????? ????????? ????????? ???????????? ??? ?????? ????????? ??????
        if (isMonthlyGoalNotSuccess(monthlyGoalAchieveResDto)) {
            return "/goal/main :: #monthly-goal-success";
        }

        model.addAttribute("monthlyGoal", monthlyGoalAchieveResDto);

        if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.COUNT) {
            return "/goal/main :: .monthly-count";
        } else if (monthlyGoalAchieveReqDto.getAchieveType() == AchieveType.TIME) {
            return "/goal/main :: .monthly-time";
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


    // ?????? ?????? ?????? ??????
    @PostMapping("/ajax/plus/daily")
    public String successDailyGoal2(@Login Member member,
                                    @ModelAttribute DailyGoalAchieveReqDto dailyGoalAchieveReqDto,
                                    Model model) {

        if (member == null) {
            return "/member/login";
        }

        // ?????? ??????
        if (dailyGoalSuccessValidator.validate(dailyGoalAchieveReqDto)) {
            return "/goal/main :: #error";
        }

        Map<String, Object> dailyGoalAndMonthlyGoalMap
                = dailyGoalAchieveService.achieveDailyGoal(member.getId(), dailyGoalAchieveReqDto.getDailyGoalId());

        DailyGoalAchieveResDto dailyGoalAchieveResDto = (DailyGoalAchieveResDto) dailyGoalAndMonthlyGoalMap.get("dailyGoal");
        model.addAttribute("goal", dailyGoalAchieveResDto);

        if (dailyGoalAchieveReqDto.getAchieveType() != AchieveType.BASIC && dailyGoalAndMonthlyGoalMap.containsKey("monthlyGoal")) {
            MonthlyGoalAchieveResDto monthlyGoalAchieveResDto = (MonthlyGoalAchieveResDto) dailyGoalAndMonthlyGoalMap.get("monthlyGoal");

            // ??? ??????
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
        } else if (dailyGoalAchieveReqDto.getAchieveType() == AchieveType.BASIC) {
            return "/goal/main :: .daily-basic-" + dailyGoalAchieveReqDto.getWeek();
        }

        return "/goal/main";
    }

    // ?????? ?????? ?????? ??????
    @PostMapping("/ajax/minus/daily")
    public String failDailyGoal2(@Login Member member,
                                 @ModelAttribute DailyGoalAchieveReqDto dailyGoalAchieveReqDto,
                                 Model model) {

        if (member == null) {
            return "/member/login";
        }

        if (dailyGoalFailValidator.validate(dailyGoalAchieveReqDto)) {
            return "/goal/main :: #error";
        }

        Map<String, Object> dailyGoalAndMonthlyGoalMap
                = dailyGoalAchieveService.failDailyGoal(member.getId(), dailyGoalAchieveReqDto.getDailyGoalId());

        DailyGoalAchieveResDto dailyGoalAchieveResDto = (DailyGoalAchieveResDto) dailyGoalAndMonthlyGoalMap.get("dailyGoal");
        model.addAttribute("goal", dailyGoalAchieveResDto);

        if (dailyGoalAchieveReqDto.getAchieveType() != AchieveType.BASIC && dailyGoalAndMonthlyGoalMap.containsKey("monthlyGoal")) {
            MonthlyGoalAchieveResDto monthlyGoalAchieveResDto = (MonthlyGoalAchieveResDto) dailyGoalAndMonthlyGoalMap.get("monthlyGoal");
            // ??? ??????
            if (monthlyGoalFailValidator.validate(monthlyGoalAchieveResDto)) {
                return "/goal/main :: #error";
            }

            model.addAttribute("monthlyGoal", monthlyGoalAchieveResDto);
        } else {
            model.addAttribute("monthlyGoal", new MonthlyGoal());
        }
        log.info("error - 2");


        if (dailyGoalAchieveReqDto.getAchieveType() == AchieveType.COUNT) {
            return "/goal/main :: .daily-count-" + dailyGoalAchieveReqDto.getWeek();
        } else if (dailyGoalAchieveReqDto.getAchieveType() == AchieveType.TIME) {
            return "/goal/main :: .daily-time-" + dailyGoalAchieveReqDto.getWeek();
        } else if (dailyGoalAchieveReqDto.getAchieveType() == AchieveType.BASIC) {
            return "/goal/main :: .daily-basic-" + dailyGoalAchieveReqDto.getWeek();
        }

        return "/goal/main";
    }

    @GetMapping("/update/monthly")
    public String getUpdateMonthlyGoal(@Login Member member,
                                       MonthlyGoalUpdateReqDto monthlyGoalUpdateReqDto,
                                       Model model) {

        log.info("update Monthly = {}", monthlyGoalUpdateReqDto);
        if (member == null) {
            return "/member/login";
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
        log.info("updateMonthlyGoal = {}", updateMonthlyGoal);

        return "/goal/updateMonthlyGoal :: #update-monthly-goal-modal";
    }

    @PostMapping("/update/monthly")
    public String updateMonthlyGoal(@Login Member member,
                                    @ModelAttribute @Validated MonthlyGoalReqDto monthlyGoalReqDto,
                                    BindingResult bindingResult) {
        if (member == null) {
            return "/member/login";
        }

        if (bindingResult.hasErrors()) {
            log.info("[POST] /update/monthly ERROR : {}", bindingResult.getAllErrors());

            return "redirect:/goal/main";
        }

        monthlyGoalUpdateService.updateMonthlyGoal(monthlyGoalReqDto);

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

        model.addAttribute("year", detailGoal.getYear());
        model.addAttribute("month", detailGoal.getMonth());
        model.addAttribute("day", detailGoal.getDay());

        return "/goal/detailDailyGoal :: #daily-goal-detail";
    }

    @GetMapping("/update/daily")
    public String getUpdateDailyGoal(@Login Member member,
                                       DailyGoalUpdateReqDto dailyGoalUpdateReqDto,
                                       Model model) {

        log.info("update Monthly = {}", dailyGoalUpdateReqDto.getDailyGoalId());
        if (member == null) {
            return "/member/login";
        }

        Map<String, String> createdGoalsMap = dailyGoalCreateService.findCreatedGoal(member.getId(), dailyGoalUpdateReqDto.getYear(), dailyGoalUpdateReqDto.getMonth());
        model.addAttribute("createdGoalsMap", createdGoalsMap);

        model.addAttribute("year", dailyGoalUpdateReqDto.getYear());
        model.addAttribute("month", dailyGoalUpdateReqDto.getMonth());
        model.addAttribute("day", dailyGoalUpdateReqDto.getDay());
        log.info("dailyGoalUpdateReqDto : {}", dailyGoalUpdateReqDto);

        DailyGoalUpdateResDto updateDailyGoal
                = dailyGoalUpdateService.getDailyGoalUpdate(dailyGoalUpdateReqDto.getDailyGoalId());
        model.addAttribute("updateDailyGoal", updateDailyGoal);


        return "/goal/updateDailyGoal :: #update-daily-goal-modal";
    }

    @PostMapping("/update/daily")
    public String updateDailyGoal(@Login Member member,
                                  @ModelAttribute @Validated DailyGoalReqDto dailyGoalReqDto,
                                  BindingResult bindingResult) {

        if (member == null) {
            return "/member/login";
        }

        if (bindingResult.hasErrors()) {
            log.info("[POST] /update/daily ERROR : {}", bindingResult.getAllErrors());

            return "redirect:/goal/main";
        }

        dailyGoalUpdateService.updateDailyGoal(dailyGoalReqDto);

        return "redirect:/goal/main";
    }

    @PostMapping("/delete/daily")
    public String deleteDailyGoal(@Login Member member,
                                  Long dailyGoalId) {
        if (member == null) {
            return "/member/login";
        }

        dailyGoalDeleteService.deleteDailyGoal(dailyGoalId, member.getId());

        return "redirect:/goal/main";
    }

    @GetMapping("/list")
    public String getGoalList(@Login Member member, int year, int month, Model model) {

        if (member == null) {
            return "/member/login";
        }

        model.addAttribute("year", year);
        model.addAttribute("month", month);

        // ?????? ?????? ?????? ?????? ?????? ?????? ?????????
        model.addAttribute("days", getDayList(year, month));

        // ?????? ?????? ?????? ?????? ?????? ?????? ?????????
        model.addAttribute("dayOfWeeks", getDayOfWeekList(year, month));
        model.addAttribute("dayOfWeekList", List.of("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"));

        // ????????? ?????? ?????? ?????? ???
        model.addAttribute("years", IntStream.rangeClosed(2000, 2100).toArray());
        model.addAttribute("months", IntStream.rangeClosed(1, 12).toArray());

        // ?????? ?????? ?????????
        List<List<DailyGoalListDto>> dailyGoalList
                = goalListService.getDailyGoalList(member.getId(), year, month, getLastDay(year, month));

        List<List<List<DailyGoalListDto>>> dailyGoals = getDailyGoalList(dailyGoalList);
        addBlankDay(dailyGoals);

        model.addAttribute("dailyGoalList", dailyGoals);

        // ?????? ?????? ?????????
        List<MonthlyGoalListDto> monthlyGoalList = goalListService.getMonthlyGoalList(member.getId(), year, month);
        model.addAttribute("monthlyGoalList", monthlyGoalList);

        return "/goal/goalList";
    }

    private void addBlankDay(List<List<List<DailyGoalListDto>>> resultList) {

        int firstWeek = resultList.get(0).size();
        for (int i = 0; i < 7 - firstWeek; i++) {
            resultList.get(0).add(0, null);
        }

        int lastWeek = resultList.get(resultList.size() - 1).size();
        for (int i = 0; i < 7 - lastWeek; i++) {
            resultList.get(resultList.size() - 1).add(null);
        }
    }

    private List<List<List<DailyGoalListDto>>> getDailyGoalList(List<List<DailyGoalListDto>> dailyGoalList) {

        List<List<List<DailyGoalListDto>>> resultList = new ArrayList<>();

        int index = 0;
        for (int i = 1; i < dailyGoalList.size(); i++) {
            List<DailyGoalListDto> dailyGoalDto = dailyGoalList.get(i);

            if (i != 1 && dailyGoalDto.get(0).getDayOfWeek().equals("MONDAY")) {
                index++;
            }

            if (resultList.size() <= index) {
                resultList.add(new ArrayList<>());
            }
            resultList.get(index).add(dailyGoalList.get(i));
        }

        return resultList;

    }

    private Integer getLastDay(int year, int month) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, 1);

        return calendar.getActualMaximum(Calendar.DATE);
    }

    private int[] getDayList(int year, int month) {

        Integer lastDay = getLastDay(year, month);

        return IntStream.rangeClosed(1, lastDay).toArray();
    }

    private List<String> getDayOfWeekList(int year, int month) {

        String[] dayOfWeeks = new String[] {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};

        int lastDay = getLastDay(year, month);

        // ?????? ?????? ?????? ?????? ?????? ????????? ????????? ??????
        int firstDayOfWeek = LocalDate.of(year, month, 1).getDayOfWeek().getValue();

        List<String> dayOfWeekList = new ArrayList<>();
        for (int i = 0; i < lastDay; i++) {
            dayOfWeekList.add(dayOfWeeks[(firstDayOfWeek - 1) % 7]);
            firstDayOfWeek++;
        }

        return dayOfWeekList;
    }
}
