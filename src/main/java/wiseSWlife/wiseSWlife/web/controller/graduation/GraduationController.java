package wiseSWlife.wiseSWlife.web.controller.graduation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import wiseSWlife.wiseSWlife.constant.GraduationConditionEnum;
import wiseSWlife.wiseSWlife.dto.graduation.TotalAcceptanceStatusTable;
import wiseSWlife.wiseSWlife.dto.graduation.form.*;
import wiseSWlife.wiseSWlife.global.session.SessionConst;
import wiseSWlife.wiseSWlife.global.session.form.SessionForm;
import wiseSWlife.wiseSWlife.service.graduation.basicCommonRequirement.BasicCommonRequirement;
import wiseSWlife.wiseSWlife.service.graduation.credit.Credit;
import wiseSWlife.wiseSWlife.service.graduation.exam.Exam;
import wiseSWlife.wiseSWlife.service.graduation.gpa.Gpa;
import wiseSWlife.wiseSWlife.service.graduation.major.Major;
import wiseSWlife.wiseSWlife.service.graduation.refinement.Refinement;
import wiseSWlife.wiseSWlife.service.graduation.totalAcceptanceStatus.TotalAcceptanceStatus;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GraduationController {
    private final static String MAJOR_BEGIN = "전공기초";
    private final static String MAJOR_SELECT = "전공선택";
    private final static String MAJOR_REQUIREMENT = "전공필수";
    private final static String REFINEMENT_SELECT = "교양선택";
    private final static String REFINEMENT_REQUIREMENT = "교양필수";
    public final static String COMPLETION_CREDIT = "이수학점";
    public final static String AVERAGE_RATING = "평균평점";
    public final static String BASIC_COMMON_REQUIREMENT = "기초공통필수";
    public final static String TRANSFER_STUDENT_EXCEPTION_MESSAGE = "편입생은 서비스를 준비중입니다...";
    public final static String TRANSFER_STUDENT_EXCEPTION_URL = "/";

    private final Exam examService;
    private final TotalAcceptanceStatus totalAcceptanceStatusScraping;
    private final Credit creditService;
    private final Gpa gpaService;
    private final Major majorService;
    private final Refinement refinementService;
    private final BasicCommonRequirement basicCommonRequirement;

    @GetMapping("/graduation")
    public String Graduation(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
                             Model model) throws IOException, InterruptedException {

        if (sessionForm.getIntCookie().isEmpty()) {
            return "redirect:/login";
        }

        if (sessionForm.getSid().substring(4, 7).equals("070")) {
            model.addAttribute("exceptionMsg", TRANSFER_STUDENT_EXCEPTION_MESSAGE);
            model.addAttribute("exceptionUri", TRANSFER_STUDENT_EXCEPTION_URL);
            return "home/home";
        }

        String intCookie = sessionForm.getIntCookie();
        String sid = sessionForm.getSid();

        //학과 + 학번에 맞는 졸업요건 상수 Enum 추출
        String graduationCondition = sessionForm.getMajor().charAt(0) + sessionForm.getSid().substring(2, 4);
        GraduationConditionEnum graduationConditionEnum = GraduationConditionEnum.valueOf(graduationCondition);
        model.addAttribute("graduationConditionEnum", graduationConditionEnum);

        TotalAcceptanceStatusTable totalAcceptanceStatusTable = totalAcceptanceStatusScraping.totalAcceptanceStatus(intCookie);

        ExamForm examForm = examService.exam(sid, intCookie);
        model.addAttribute("examForm", examForm);

        MajorForm majorForm = majorService.getMajorForm(sid, totalAcceptanceStatusTable.getBody().get(MAJOR_BEGIN), totalAcceptanceStatusTable.getBody().get(MAJOR_SELECT), totalAcceptanceStatusTable.getBody().get(MAJOR_REQUIREMENT));
        model.addAttribute("majorForm", majorForm);

        RefinementForm refinementForm = refinementService.getRefinementForm(sid, totalAcceptanceStatusTable.getBody().get(REFINEMENT_SELECT), totalAcceptanceStatusTable.getBody().get(REFINEMENT_REQUIREMENT));
        model.addAttribute("refinementForm", refinementForm);

        CreditForm creditForm = creditService.getCredit(sid, Integer.parseInt(totalAcceptanceStatusTable.getSummary().get(COMPLETION_CREDIT)));
        model.addAttribute("creditForm", creditForm);

        GPAForm gpaForm = gpaService.getGpa(sid, Double.parseDouble(totalAcceptanceStatusTable.getSummary().get(AVERAGE_RATING)));
        model.addAttribute("gpaForm", gpaForm);

        BCRForm bcrForm = basicCommonRequirement.getBCR(sid, totalAcceptanceStatusTable.getBody().get(BASIC_COMMON_REQUIREMENT), totalAcceptanceStatusTable.getBody().get(REFINEMENT_REQUIREMENT));
        model.addAttribute("bcrForm", bcrForm);

        model.addAttribute("sessionForm", sessionForm);
        return "graduate/graduation";
    }

}
