package wiseSWlife.wiseSWlife.web.controller.graduation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import wiseSWlife.wiseSWlife.db.repository.bcrRepository.BCRRepository;
import wiseSWlife.wiseSWlife.db.repository.examRepository.ExamRepository;
import wiseSWlife.wiseSWlife.db.repository.gpaRepository.GPARepository;
import wiseSWlife.wiseSWlife.db.repository.majorRepository.MajorRepository;
import wiseSWlife.wiseSWlife.db.repository.refinementRepository.RefinementRepository;
import wiseSWlife.wiseSWlife.db.repository.totalCreditRepository.TotalCreditRepository;
import wiseSWlife.wiseSWlife.global.session.SessionConst;
import wiseSWlife.wiseSWlife.global.session.form.SessionForm;
import wiseSWlife.wiseSWlife.dto.graduation.ExamTable;
import wiseSWlife.wiseSWlife.dto.graduation.TotalAcceptanceStatusTable;
import wiseSWlife.wiseSWlife.dto.graduation.form.*;
import wiseSWlife.wiseSWlife.service.graduation.basicCommonRequirementInf.BasicCommonRequirement;
import wiseSWlife.wiseSWlife.service.graduation.scrapingInterface.ExamScraping;
import wiseSWlife.wiseSWlife.service.graduation.conditionInf.Condition;
//import wiseSWlife.wiseSWlife.service.enumMapper.EnumMapperFactory;
import wiseSWlife.wiseSWlife.service.graduation.scrapingInterface.TotalAcceptanceStatusScraping;
import wiseSWlife.wiseSWlife.constant.GraduationConditionEnum;

import java.io.IOException;
import java.sql.SQLException;
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
//    private final EnumMapperFactory enumMapperFactory;
    private final ExamRepository examRepository;
    private final MajorRepository majorRepository;
    private final RefinementRepository refinementRepo;
    private final TotalCreditRepository totalCreditRepository;
    private final GPARepository gpaRepository;
    private final BCRRepository bcrRepository;

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

        GraduationConditionEnum graduationConditionEnum = null;

        //List<GraduationConditionEnumMapperValue> list = enumMapperFactory.get("GraduationCondition");
//        for(GraduationConditionEnum graduationConditionEnum : GraduationConditionEnum.values()){
//            if(Objects.equals(graduationConditionEnum.getCode(), groupName)){
//                condition = graduationConditionEnum;
//                break;
//            }
//        }

        graduationConditionEnum = GraduationConditionEnum.valueOf(groupName);

        //졸업요건표
        model.addAttribute("graduationConditionEnum", graduationConditionEnum);
        TotalAcceptanceStatusTable totalAcceptanceStatusTable = totalAcceptanceStatusScraping.scrapping(intCookie);

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
            MajorForm majorForm = this.condition.checkMajor(sid, totalAcceptanceStatusTable.getBody().get("전공기초"), totalAcceptanceStatusTable.getBody().get("전공선택"), totalAcceptanceStatusTable.getBody().get("전공필수"));

            majorRepository.save(majorForm);
            model.addAttribute("majorForm", majorForm);
        }else{
            model.addAttribute("majorForm", majorBySid.get());
        }

        Optional<RefinementForm> refinementBySid = refinementRepo.findRefinementBySid(sid);
        if(refinementBySid.isEmpty()){
            RefinementForm refinementForm = this.condition.checkRefinement(sid, totalAcceptanceStatusTable.getBody().get("교양선택"), totalAcceptanceStatusTable.getBody().get("교양필수"));

            refinementRepo.save(refinementForm);
            model.addAttribute("refinementForm", refinementForm);
        }else{
            model.addAttribute("refinementForm", refinementBySid.get());
        }

        Optional<CreditForm> totalCreditBySid = totalCreditRepository.findTotalCreditBySid(sid);
        if(totalCreditBySid.isEmpty()){
            CreditForm creditForm = this.condition.checkCredit(sid, Integer.parseInt(totalAcceptanceStatusTable.getSummary().get("이수학점")));

            totalCreditRepository.save(creditForm);
            model.addAttribute("creditForm", creditForm);
        }else{
            model.addAttribute("creditForm", totalCreditBySid.get());
        }

        Optional<GPAForm> gpaBySid = gpaRepository.findGPABySid(sid);
        if(gpaBySid.isEmpty()){
            GPAForm gpaForm = this.condition.checkGPA(sid, Double.parseDouble(totalAcceptanceStatusTable.getSummary().get("평점평균")));

            gpaRepository.save(gpaForm);
            model.addAttribute("gpaForm", gpaForm);
        }else{
            model.addAttribute("gpaForm", gpaBySid.get());
        }

        Optional<BCRForm> bcrBySid = bcrRepository.findBCRBySid(sid);
        if(bcrBySid.isEmpty()){
            BCRForm bcrForm = basicCommonRequirement.parse(sid, totalAcceptanceStatusTable.getBody().get("기초공통필수"), totalAcceptanceStatusTable.getBody().get("교양필수"));

            bcrRepository.save(bcrForm);
            model.addAttribute("bcrForm", bcrForm);
        }else{
            model.addAttribute("bcrForm", bcrBySid.get());
        }

        model.addAttribute("sessionForm", sessionForm);
        return "graduate/graduation";
    }

}
