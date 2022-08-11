package kong.tues.goal.presentation;

import kong.tues.commons.dto.ResponseDTO;
import kong.tues.goal.dailyGoal.application.*;
import kong.tues.goal.dailyGoal.application.dto.*;
import kong.tues.goal.dailyGoal.domain.DailyGoal;
import kong.tues.goal.dailyGoal.presentation.dto.DailyGoalReqDto;
import kong.tues.goal.dailyGoal.presentation.validator.DailyGoalReqDtoValidator;
import kong.tues.goal.goalList.application.GoalListService;
import kong.tues.goal.mothlyGoal.application.*;
import kong.tues.goal.mothlyGoal.domain.MonthlyGoal;
import kong.tues.goal.mothlyGoal.dto.*;
import kong.tues.goal.mothlyGoal.presentation.validator.MonthlyGoalReqDtoValidator;
import kong.tues.goal.presentation.dto.DateResponse;
import kong.tues.goal.presentation.dto.MainResponse;
import kong.tues.member.SessionConst;
import kong.tues.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

@Slf4j
@RequestMapping("/api/main")
@RequiredArgsConstructor
@RestController
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

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(memberJoinReqDtoValidator);
        dataBinder.addValidators(dailyGoalReqDtoValidator);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> main(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 월간목표를 위한 데이터
        List<MonthlyGoalMainResDto> monthlyGoals
                = monthlyGoalFindService.findMonthlyGoals(member.getId(), LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        // 주간 목표를 위한 데이터
        Map<String, List<DailyGoalMainResDto>> dailyGoals = dailyGoalFindService.findWeeklyGoals(member.getId(), LocalDate.now().getYear(), LocalDate.now().getMonthValue(),
                LocalDate.now().getDayOfMonth());

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(MainResponse.builder().dateResponse(new DateResponse()).monthlyGoals(monthlyGoals).dailyGoals(dailyGoals).build())
                .build());
    }

    // 월간 목표 개수 감소
    @PostMapping("/minus/monthly/{monthlyGoalId}")
    public ResponseEntity<ResponseDTO> minusMonthlyGoal(@PathVariable Long monthlyGoalId) {

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(monthlyGoalAchieveService.minusMonthlyGoal(monthlyGoalId)).build());
    }

    // 월간 목표 개수 증가
    @PostMapping("/plus/monthly/{monthlyGoalId}")
    public ResponseEntity<ResponseDTO> plushMonthlyGoal(@PathVariable Long monthlyGoalId) {

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(monthlyGoalAchieveService.plusMonthlyGoal(monthlyGoalId)).build());
    }

    // 일간 목표 개수 감소
    @PostMapping("/minus/daily/{dailyGoalId}")
    public ResponseEntity<ResponseDTO> minusDailyGoal(@PathVariable Long dailyGoalId) {

        DailyGoal dailyGoal = dailyGoalAchieveService.minusDailyGoal(dailyGoalId);
        MonthlyGoal monthlyGoal = dailyGoal.getMonthlyGoal();

        GoalAchieveResDto goalAchieveResDto = new GoalAchieveResDto();
        if (monthlyGoal != null) {
            goalAchieveResDto.setMonthlyGoals(monthlyGoalFindService.findMonthlyGoals(dailyGoal.getMember().getId(), LocalDate.now().getYear(), LocalDate.now().getMonthValue()));
        }

        goalAchieveResDto.setDailyGoals(dailyGoalFindService.findDailyGoalsByDate(dailyGoal.getDate(), dailyGoal.getMember()));

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(goalAchieveResDto).build());
    }

    // 일간 목표 개수 증가
    @PostMapping("/plus/daily/{dailyGoalId}")
    public ResponseEntity<ResponseDTO> plusDailyGoal(@PathVariable Long dailyGoalId) {

        DailyGoal dailyGoal = dailyGoalAchieveService.plusDailyGoal(dailyGoalId);
        MonthlyGoal monthlyGoal = dailyGoal.getMonthlyGoal();

        GoalAchieveResDto goalAchieveResDto = new GoalAchieveResDto();
        if (monthlyGoal != null) {
            goalAchieveResDto.setMonthlyGoals(monthlyGoalFindService.findMonthlyGoals(dailyGoal.getMember().getId(), LocalDate.now().getYear(), LocalDate.now().getMonthValue()));
        }

        goalAchieveResDto.setDailyGoals(dailyGoalFindService.findDailyGoalsByDate(dailyGoal.getDate(), dailyGoal.getMember()));

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(goalAchieveResDto).build());
    }

    // 월간 목표 상세
    @GetMapping("/detail/monthly/{monthlyGoalId}")
    public ResponseEntity<ResponseDTO> detailMonthly(@PathVariable Long monthlyGoalId) {

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(monthlyGoalDetailService.getMonthlyGoalDetail(monthlyGoalId))
                .build());
    }

    // 월간 목표 삭제
    @PostMapping("/monthly/delete/{monthlyGoalId}")
    public ResponseEntity<ResponseDTO> deleteMonthly(@PathVariable Long monthlyGoalId) {

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(monthlyGoalDeleteService.deleteMonthlyGoal(monthlyGoalId))
                .build());
    }

    // 월간 목표 수정 화면
    @GetMapping("/monthly/update/{monthlyGoalId}")
    public ResponseEntity<ResponseDTO> modifyMonthlyView(@PathVariable Long monthlyGoalId) {

        List<String> createdGoalTypes = monthlyGoalCreateService.findCreatedGoalTypes(monthlyGoalId);
        MonthlyGoalUpdateResDto updateMonthlyGoal = monthlyGoalUpdateService.getMonthlyGoalUpdate(monthlyGoalId);

        Map<String, Object> data = new HashMap<>();
        data.put("createdGoalTypes", createdGoalTypes);
        data.put("updateMonthlyGoal", updateMonthlyGoal);

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(data)
                .build());
    }

    // 월간 목표 수정
    @PostMapping("/monthly/update")
    public ResponseEntity<ResponseDTO> modifyMonthly(@RequestBody @Valid MonthlyGoalReqDto monthlyGoalReqDto) {

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(monthlyGoalUpdateService.updateMonthlyGoal(monthlyGoalReqDto))
                .build());
    }

    // 일간 목표 상세
    @GetMapping("/daily/detail/{dailyGoalId}")
    public ResponseEntity<ResponseDTO> detailDaily(@PathVariable Long dailyGoalId) {

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(dailyGoalDetailService.getDailyGoalDetail(dailyGoalId)).build());
    }

    // 월간 목표 수정 화면
    @GetMapping("/daily/update/{dailyGoalId}")
    public ResponseEntity<ResponseDTO> modifyDailyView(@PathVariable Long dailyGoalId) {

        DailyGoalUpdateResDto dailyGoal = dailyGoalUpdateService.getDailyGoalUpdate(dailyGoalId);
        List<MonthlyGoalMainResDto> monthlyGoals = monthlyGoalFindService.getMonthlyGoalNames(dailyGoalId);

        Map<String, Object> data = new HashMap<>();
        data.put("dailyGoal", dailyGoal);
        data.put("monthlyGoals", monthlyGoals);

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(data)
                .build());
    }

    // 일간 목표 수정
    @PostMapping("/daily/update")
    public ResponseEntity<ResponseDTO> modifyDaily(@RequestBody @Valid DailyGoalReqDto dailyGoalReqDto) {

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(dailyGoalUpdateService.updateDailyGoal(dailyGoalReqDto)).build());
    }

    // 일간 목표 삭제
    @DeleteMapping("/daily/{dailyGoalId}")
    public ResponseEntity<ResponseDTO> deleteDaily(@PathVariable Long dailyGoalId) {

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(dailyGoalDeleteService.deleteDailyGoal(dailyGoalId))
                .build()
        );
    }

    // 월간 목표 생성 날짜 지정
    @GetMapping("/monthly/view/{year}/{month}")
    public ResponseEntity<ResponseDTO> createMonthlyGoalView(@PathVariable Integer year,
                                                             @PathVariable Integer month,
                                                             HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(monthlyGoalCreateService.findCreatedGoalTypes(member.getId(), year, month)).build());
    }

    // 월간 목표 생성
    @PostMapping("/monthly")
    public ResponseEntity<ResponseDTO> createMonthlyGoal(@RequestBody @Valid MonthlyGoalReqDto monthlyGoalReqDto,
                                                         HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(monthlyGoalCreateService.save(monthlyGoalReqDto, member.getId())).build());
    }

    // 일간 목표 생성 화면
    @GetMapping("/daily/view/{year}/{month}")
    public ResponseEntity<ResponseDTO> createDailyGoalView(@PathVariable Integer year,
                                                           @PathVariable Integer month,
                                                           HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(dailyGoalCreateService.findCreatedGoal(member.getId(), year, month)).build());
    }

    // 일간 목표 생성
    @PostMapping("/daily")
    public ResponseEntity<ResponseDTO> createDailyGoal(@RequestBody @Valid DailyGoalReqDto dailyGoalReqDto,
                                                       HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(dailyGoalCreateService.save(dailyGoalReqDto, member.getId())).build());
    }

    // 목표 리스트 불러오기
    @GetMapping("/list/{year}/{month}")
    public ResponseEntity<ResponseDTO> getGoalList(@PathVariable Integer year,
                                                   @PathVariable Integer month,
                                                   HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Map<String, List> data = new HashMap<>();
        data.put("goalList", goalListService.getGoalList(year, month, member.getId()));
        data.put("days", goalListService.getDays(year, month));
        data.put("monthlyGoals", monthlyGoalFindService.findMonthlyGoals(member.getId(), year, month));

        return ResponseEntity.ok(ResponseDTO.builder()
                .data(data).build());
    }
}
