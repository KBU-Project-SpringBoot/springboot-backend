package wiseSWlife.wiseSWlife.web.controller.graduation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import wiseSWlife.wiseSWlife.global.session.SessionConst;
import wiseSWlife.wiseSWlife.global.session.form.SessionForm;
import wiseSWlife.wiseSWlife.model.graduation.TotalAcceptanceStatusTable;
import wiseSWlife.wiseSWlife.model.graduation.form.BCRForm;
import wiseSWlife.wiseSWlife.model.graduation.form.CreditForm;
import wiseSWlife.wiseSWlife.model.graduation.form.GPAForm;
import wiseSWlife.wiseSWlife.service.graduation.basicCommonRequirement.ParsingBCR;
import wiseSWlife.wiseSWlife.service.graduation.scrapping.Exam;
import wiseSWlife.wiseSWlife.service.graduation.scrapping.TotalAcceptanceStatus;
import wiseSWlife.wiseSWlife.service.graduation.standardImpl.Standard2017;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GraduationController {

    private final Exam exam;
    private final TotalAcceptanceStatus totalAcceptanceStatus;
    private final Standard2017 standard2017;
    private final ParsingBCR parsingBCR;

    @GetMapping("/graduation")
    public String Graduation(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY,required = false)SessionForm sessionForm,
                             Model model) throws IOException, InterruptedException {

        if(sessionForm.getIntCookie().isEmpty()){
            return "redirect:/login";
        }

        String intCookie = sessionForm.getIntCookie();
        Map<String, Boolean> examMap = exam.scrapping(intCookie);
        TotalAcceptanceStatusTable totalAcceptanceStatusTable = totalAcceptanceStatus.scrapping(intCookie);

        String sid = sessionForm.getSid().substring(0,4);
        System.out.println("너는 허허 : " + totalAcceptanceStatusTable);


        BCRForm bcrForm = parsingBCR.getStudy(totalAcceptanceStatusTable.getBody().get("기초공통필수"));
        model.addAttribute("bcrForm", bcrForm);


        if(sid.equals("2017")){
            CreditForm creditForm = standard2017.percentageGraduationCredit(Integer.parseInt(totalAcceptanceStatusTable.getSummary().get("이수학점")));
            model.addAttribute("creditForm", creditForm);
            GPAForm gpaForm = standard2017.percentageGradationGPA(Double.parseDouble(totalAcceptanceStatusTable.getSummary().get("평점평균")));
            model.addAttribute("gpaForm", gpaForm);
        }

        model.addAttribute("examMap", examMap);
        model.addAttribute("sessionForm", sessionForm);
        return "graduate/graduation";
    }

}
