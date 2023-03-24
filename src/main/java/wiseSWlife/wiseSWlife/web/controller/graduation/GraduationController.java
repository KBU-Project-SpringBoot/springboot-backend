package wiseSWlife.wiseSWlife.web.controller.graduation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import wiseSWlife.wiseSWlife.constant.GraduationConditionEnum;
import wiseSWlife.wiseSWlife.db.repository.bcrRepository.BCRRepository;
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
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GraduationController {

    private final Exam examService;
    private final TotalAcceptanceStatus totalAcceptanceStatusScraping;
    private final Credit creditService;
    private final Gpa gpaService;
    private final Major majorService;
    private final Refinement refinementService;
    private final BasicCommonRequirement basicCommonRequirement;
    private final BCRRepository bcrRepository;

    @GetMapping("/graduation")
    public String Graduation(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
                             Model model) throws IOException, InterruptedException {

        if (sessionForm.getIntCookie().isEmpty()) {
            return "redirect:/login";
        }

        if (sessionForm.getSid().substring(4, 7).equals("070")) {
            model.addAttribute("exceptionMsg", "편입생은 서비스를 준비중입니다...");
            model.addAttribute("exceptionUri", "/");
            return "home/home";
        }

        String intCookie = sessionForm.getIntCookie();
        String sid = sessionForm.getSid();

        //학과 + 학번에 맞는 졸업요건 상수 Enum 추출
        String graduationCondition = sessionForm.getMajor().charAt(0) + sessionForm.getSid().substring(2, 4);
        GraduationConditionEnum graduationConditionEnum = GraduationConditionEnum.valueOf(graduationCondition);
        model.addAttribute("graduationConditionEnum", graduationConditionEnum);

        TotalAcceptanceStatusTable totalAcceptanceStatusTable = totalAcceptanceStatusScraping.scrapping(intCookie);

        ExamForm examForm = examService.exam(sid, intCookie);
        model.addAttribute("examForm", examForm);

        MajorForm majorForm = majorService.getMajorForm(sid, totalAcceptanceStatusTable.getBody().get("전공기초"), totalAcceptanceStatusTable.getBody().get("전공선택"), totalAcceptanceStatusTable.getBody().get("전공필수"));
        model.addAttribute("majorForm", majorForm);

        RefinementForm refinementForm = refinementService.getRefinementForm(sid, totalAcceptanceStatusTable.getBody().get("교양선택"), totalAcceptanceStatusTable.getBody().get("교양필수"));
        model.addAttribute("refinementForm", refinementForm);

        CreditForm creditForm = creditService.getCredit(sid, Integer.parseInt(totalAcceptanceStatusTable.getSummary().get("이수학점")));
        model.addAttribute("creditForm", creditForm);

        GPAForm gpaForm = gpaService.getGpa(sid, Double.parseDouble(totalAcceptanceStatusTable.getSummary().get("평점평균")));
        model.addAttribute("gpaForm", gpaForm);

        Optional<BCRForm> bcrBySid = bcrRepository.findBCRBySid(sid);
        if (bcrBySid.isEmpty()) {
            BCRForm bcrForm = basicCommonRequirement.parse(sid, totalAcceptanceStatusTable.getBody().get("기초공통필수"), totalAcceptanceStatusTable.getBody().get("교양필수"));

            bcrRepository.save(bcrForm);
            model.addAttribute("bcrForm", bcrForm);
        } else {
            model.addAttribute("bcrForm", bcrBySid.get());
        }

        model.addAttribute("sessionForm", sessionForm);
        return "graduate/graduation";
    }

}
