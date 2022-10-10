package wiseSWlife.wiseSWlife.web.controller.graduation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import wiseSWlife.wiseSWlife.global.session.SessionConst;
import wiseSWlife.wiseSWlife.global.session.form.SessionForm;
import wiseSWlife.wiseSWlife.model.graduation.ExamTable;
import wiseSWlife.wiseSWlife.model.graduation.TotalAcceptanceStatusTable;
import wiseSWlife.wiseSWlife.model.graduation.form.*;
import wiseSWlife.wiseSWlife.service.graduation.basicCommonRequirement.ParsingBCR;
import wiseSWlife.wiseSWlife.service.graduation.convertScrapingInf.ConvertExamTable;
import wiseSWlife.wiseSWlife.service.graduation.scrapingImpl.Exam;
import wiseSWlife.wiseSWlife.service.graduation.scrapingImpl.TotalAcceptanceStatus;
import wiseSWlife.wiseSWlife.service.graduation.standardImpl.Standard2017;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GraduationController {

    private final Exam exam;
    private final ConvertExamTable convertExamTable;
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
        String sid = sessionForm.getSid().substring(0,4);

        //졸업 시험 테이블 추출
        ExamTable examTable = exam.scrapping(intCookie);
        Map<String, Boolean> examMap = convertExamTable.convert(examTable);

        model.addAttribute("examMap", examMap);

        //전체이수 현황 테이블 추출
        TotalAcceptanceStatusTable totalAcceptanceStatusTable = totalAcceptanceStatus.scrapping(intCookie);

        BCRForm bcrForm = parsingBCR.getStudy(totalAcceptanceStatusTable.getBody().get("기초공통필수"), totalAcceptanceStatusTable.getBody().get("교양필수"));
        model.addAttribute("bcrForm", bcrForm);

        MajorForm majorForm = standard2017.checkMajor(totalAcceptanceStatusTable.getBody().get("전공기초"), totalAcceptanceStatusTable.getBody().get("전공선택"), totalAcceptanceStatusTable.getBody().get("전공필수"));
        model.addAttribute("majorForm", majorForm);

        RefinementForm refinementForm = standard2017.checkRefinement(totalAcceptanceStatusTable.getBody().get("교양선택"), totalAcceptanceStatusTable.getBody().get("교양필수"));
        model.addAttribute("refinementForm", refinementForm);

        if(sid.equals("2017")){
            CreditForm creditForm = standard2017.percentageGraduationCredit(Integer.parseInt(totalAcceptanceStatusTable.getSummary().get("이수학점")));
            model.addAttribute("creditForm", creditForm);
            GPAForm gpaForm = standard2017.percentageGradationGPA(Double.parseDouble(totalAcceptanceStatusTable.getSummary().get("평점평균")));
            model.addAttribute("gpaForm", gpaForm);
        }


        model.addAttribute("sessionForm", sessionForm);
        return "graduate/graduation";
    }

}
