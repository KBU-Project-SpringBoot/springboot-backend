package wiseSWlife.wiseSWlife.web.controller.graduation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import wiseSWlife.wiseSWlife.db.repository.examRepository.ExamRepository;
import wiseSWlife.wiseSWlife.db.repository.majorRepository.MajorRepository;
import wiseSWlife.wiseSWlife.db.repository.refinementRepo.RefinementRepo;
import wiseSWlife.wiseSWlife.db.repository.totalCreditRepository.TotalCreditRepository;
import wiseSWlife.wiseSWlife.global.session.SessionConst;
import wiseSWlife.wiseSWlife.global.session.form.SessionForm;
import wiseSWlife.wiseSWlife.model.graduation.ExamTable;
import wiseSWlife.wiseSWlife.model.graduation.TotalAcceptanceStatusTable;
import wiseSWlife.wiseSWlife.model.graduation.form.*;
import wiseSWlife.wiseSWlife.service.graduation.basicCommonRequirementInf.BasicCommonRequirement;
import wiseSWlife.wiseSWlife.service.graduation.scrapingInterface.ExamScraping;
import wiseSWlife.wiseSWlife.service.graduation.conditionInf.Condition;
import wiseSWlife.wiseSWlife.service.enumMapper.EnumMapperFactory;
import wiseSWlife.wiseSWlife.model.graduationConditionEnumMapper.GraduationConditionEnumMapperValue;
import wiseSWlife.wiseSWlife.service.graduation.scrapingInterface.TotalAcceptanceStatusScraping;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GraduationController {

    private final ExamScraping examScraping;
    private final TotalAcceptanceStatusScraping totalAcceptanceStatusScraping;
    private final Condition condition;
    private final BasicCommonRequirement basicCommonRequirement;
    private final EnumMapperFactory enumMapperFactory;
    private final ExamRepository examRepository;
    private final MajorRepository majorRepository;
    private final RefinementRepo refinementRepo;
    private final TotalCreditRepository totalCreditRepository;

    @GetMapping("/graduation")
    public String Graduation(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY,required = false)SessionForm sessionForm,
                             Model model) throws IOException, InterruptedException, SQLException {

        if(sessionForm.getIntCookie().isEmpty()){
            return "redirect:/login";
        }
        if(sessionForm.getSid().substring(4, 7).equals("070")){
            model.addAttribute("exceptionMsg", "편입생은 서비스를 준비중입니다...");
            model.addAttribute("exceptionUri", "/");
            return "home/home";
        }

        String intCookie = sessionForm.getIntCookie();
        String sid = sessionForm.getSid();
        String groupName = sessionForm.getMajor().charAt(0) + sessionForm.getSid().substring(2,4);


        GraduationConditionEnumMapperValue condition = null;

        List<GraduationConditionEnumMapperValue> list = enumMapperFactory.get("GraduationCondition");
        for(GraduationConditionEnumMapperValue i : list){
            if(Objects.equals(i.getCode(), groupName)){
                condition = i;
                break;
            }
        }
        //졸업요건표
        model.addAttribute("vo", condition);

        //졸업 시험 테이블 추출
        Optional<ExamForm> examBySid = examRepository.findExamBySid(sid);
        if(examBySid.isEmpty()){
            ExamTable examTable = examScraping.scraping(intCookie);
            ExamForm examForm = examScraping.convert(sid, examTable);//key : 시험이름, value : 통과여부
            examRepository.save(examForm);
            model.addAttribute("examForm", examForm);
        }else{
            model.addAttribute("examForm", examBySid.get());
        }

        Optional<MajorForm> majorBySid = majorRepository.findMajorBySid(sid);
        if(majorBySid.isEmpty()){
            TotalAcceptanceStatusTable totalAcceptanceStatusTable = totalAcceptanceStatusScraping.scrapping(intCookie);
            MajorForm majorForm = this.condition.checkMajor(sid, totalAcceptanceStatusTable.getBody().get("전공기초"), totalAcceptanceStatusTable.getBody().get("전공선택"), totalAcceptanceStatusTable.getBody().get("전공필수"));

            majorRepository.save(majorForm);
            model.addAttribute("majorForm", majorForm);
        }else{
            model.addAttribute("majorForm", majorBySid.get());
        }

        Optional<RefinementForm> refinementBySid = refinementRepo.findRefinementBySid(sid);
        if(refinementBySid.isEmpty()){
            TotalAcceptanceStatusTable totalAcceptanceStatusTable = totalAcceptanceStatusScraping.scrapping(intCookie);
            RefinementForm refinementForm = this.condition.checkRefinement(sid, totalAcceptanceStatusTable.getBody().get("교양선택"), totalAcceptanceStatusTable.getBody().get("교양필수"));

            refinementRepo.save(refinementForm);
            model.addAttribute("refinementForm", refinementForm);
        }

        Optional<CreditForm> totalCreditBySid = totalCreditRepository.findTotalCreditBySid(sid);
        if(totalCreditBySid.isEmpty()){
            TotalAcceptanceStatusTable totalAcceptanceStatusTable = totalAcceptanceStatusScraping.scrapping(intCookie);
            CreditForm creditForm = this.condition.checkCredit(sid, Integer.parseInt(totalAcceptanceStatusTable.getSummary().get("이수학점")));

            totalCreditRepository.save(creditForm);
            model.addAttribute("creditForm", creditForm);
        }

        //전체이수 현황 테이블 추출
        TotalAcceptanceStatusTable totalAcceptanceStatusTable = totalAcceptanceStatusScraping.scrapping(intCookie);

        BCRForm bcrForm = basicCommonRequirement.parse(sid, totalAcceptanceStatusTable.getBody().get("기초공통필수"), totalAcceptanceStatusTable.getBody().get("교양필수"));
        model.addAttribute("bcrForm", bcrForm);

        GPAForm gpaForm = this.condition.checkGPA(sid, Double.parseDouble(totalAcceptanceStatusTable.getSummary().get("평점평균")));
        model.addAttribute("gpaForm", gpaForm);

        model.addAttribute("sessionForm", sessionForm);
        return "graduate/graduation";
    }

}
