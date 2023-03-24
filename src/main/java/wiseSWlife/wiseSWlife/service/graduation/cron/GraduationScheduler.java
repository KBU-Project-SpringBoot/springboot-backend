package wiseSWlife.wiseSWlife.service.graduation.cron;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.db.repository.bcrRepository.BCRRepository;
import wiseSWlife.wiseSWlife.db.repository.gpaRepository.GPARepository;
import wiseSWlife.wiseSWlife.db.repository.intranetRepository.IntranetRepository;
import wiseSWlife.wiseSWlife.db.repository.majorRepository.MajorRepository;
import wiseSWlife.wiseSWlife.db.repository.refinementRepository.RefinementRepository;
import wiseSWlife.wiseSWlife.db.repository.totalCreditRepository.TotalCreditRepository;
import wiseSWlife.wiseSWlife.dto.graduation.TotalAcceptanceStatusTable;
import wiseSWlife.wiseSWlife.dto.graduation.form.*;
import wiseSWlife.wiseSWlife.dto.intranet.Intranet;
import wiseSWlife.wiseSWlife.dto.member.Member;
import wiseSWlife.wiseSWlife.service.graduation.basicCommonRequirement.BasicCommonRequirement;
import wiseSWlife.wiseSWlife.service.graduation.credit.Credit;
import wiseSWlife.wiseSWlife.service.graduation.exam.Exam;
import wiseSWlife.wiseSWlife.service.graduation.gpa.Gpa;
import wiseSWlife.wiseSWlife.service.graduation.major.Major;
import wiseSWlife.wiseSWlife.service.graduation.refinement.Refinement;
import wiseSWlife.wiseSWlife.service.graduation.totalAcceptanceStatus.TotalAcceptanceStatus;
import wiseSWlife.wiseSWlife.service.login.LoginService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 성적 발표일 자정에 성적과 졸업평가를 스크래핑하여 데이터베이스에 저장하는 코드
 * 문제 : 년도별 성적 발표일시가 일정하지 않아 수정이 필요해보임
 */
@Service
@RequiredArgsConstructor
public class GraduationScheduler {

    private final IntranetRepository intranetRepository;
    private final LoginService loginService;
    private final BasicCommonRequirement basicCommonRequirement;
    private final Credit creditService;
    private final Gpa gpaService;
    private final Major majorService;
    private final Refinement refinementService;
    private final Exam examService;
    private final TotalAcceptanceStatus totalAcceptanceStatusScraping;
    private final MajorRepository majorRepository;
    private final RefinementRepository refinementRepo;
    private final TotalCreditRepository totalCreditRepository;
    private final GPARepository gpaRepository;
    private final BCRRepository bcrRepository;

    /**
     * 1학기 성적 확인 일정
     * 22년도 1학기 최종 성적 발표일시(2022년 7월 15일(금))
     * <p>
     * 시험 스크래핑 작업
     * <p>
     * 1. Login_TB의 모든 ID, PW를 가져온다.
     * <p>
     * 2. ID, PW의 반복문
     * <p>
     * 2-1. Login scraping 수행(loginForm 추출)
     * 2-2. Exam scraping 수행
     * 2-3. Exam_TB에 저장
     */
    @Scheduled(cron = "0 0 0 15 7 ?")
    public void test() throws IOException, InterruptedException, SQLException {
        List<Intranet> intranets = intranetRepository.findAll();

        for (Intranet user : intranets) {
            Member loginMember = loginService.login(user.getIntranetId(), user.getIntranetPw());

            if (loginMember.getSid().startsWith("070", 4)) {
                continue;
            }
            String intCookie = loginMember.getIntCookie();
            String sid = loginMember.getSid();
//            String groupName = loginMember.getMajor().charAt(0) + loginMember.getSid().substring(2, 4);
//
//            GraduationConditionEnumMapperValue condition = null;

            //List<GraduationConditionEnumMapperValue> list = enumMapperFactory.get("GraduationCondition");
//            for (GraduationConditionEnum i : GraduationConditionEnum.values()) {
//                if (Objects.equals(i.getCode(), groupName)) {
//                    condition = i;
//                    break;
//                }
//            }

            //졸업 시험 테이블 추출
            ExamForm exam = examService.exam(sid, intCookie);

            //전체 이수 현황 테이블 추출
            TotalAcceptanceStatusTable totalAcceptanceStatusTable = totalAcceptanceStatusScraping.scrapping(loginMember.getIntCookie());

            MajorForm majorForm = majorService.getMajorForm(sid, totalAcceptanceStatusTable.getBody().get("전공기초"), totalAcceptanceStatusTable.getBody().get("전공선택"), totalAcceptanceStatusTable.getBody().get("전공필수"));
            majorRepository.update(majorForm);
            RefinementForm refinementForm = refinementService.getRefinementForm(sid, totalAcceptanceStatusTable.getBody().get("교양선택"), totalAcceptanceStatusTable.getBody().get("교양필수"));
            refinementRepo.update(refinementForm);
            CreditForm creditForm = creditService.credit(sid, Integer.parseInt(totalAcceptanceStatusTable.getSummary().get("이수학점")));
            totalCreditRepository.update(creditForm);
            GPAForm gpaForm = gpaService.checkGPA(sid, Double.parseDouble(totalAcceptanceStatusTable.getSummary().get("평점평균")));
            gpaRepository.update(gpaForm);
            BCRForm bcrForm = basicCommonRequirement.parse(sid, totalAcceptanceStatusTable.getBody().get("기초공통필수"), totalAcceptanceStatusTable.getBody().get("교양필수"));
            bcrRepository.update(bcrForm);
        }

    }


    //2학기 성적 확인 일정
    //21년도 2학기 최종 성적 발표일시(2022년 1월 7일(금))
    @Scheduled(cron = "0 0 0 7 1 ?")
    public void test2() {
        System.out.println("현재 시간은 " + new Date());
    }
}
