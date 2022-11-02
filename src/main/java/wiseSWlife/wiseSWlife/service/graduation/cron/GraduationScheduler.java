package wiseSWlife.wiseSWlife.service.graduation.cron;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.db.repository.examRepository.ExamRepository;
import wiseSWlife.wiseSWlife.db.repository.intranetRepository.IntranetRepository;
import wiseSWlife.wiseSWlife.db.repository.majorRepository.MajorRepository;
import wiseSWlife.wiseSWlife.model.graduation.ExamTable;
import wiseSWlife.wiseSWlife.model.graduation.TotalAcceptanceStatusTable;
import wiseSWlife.wiseSWlife.model.graduation.form.ExamForm;
import wiseSWlife.wiseSWlife.model.graduation.form.MajorForm;
import wiseSWlife.wiseSWlife.model.graduationConditionEnumMapper.GraduationConditionEnumMapperValue;
import wiseSWlife.wiseSWlife.model.intranet.Intranet;
import wiseSWlife.wiseSWlife.model.member.Member;
import wiseSWlife.wiseSWlife.service.enumMapper.EnumMapperFactory;
import wiseSWlife.wiseSWlife.service.graduation.conditionInf.Condition;
import wiseSWlife.wiseSWlife.service.graduation.scrapingInterface.ExamScraping;
import wiseSWlife.wiseSWlife.service.graduation.scrapingInterface.TotalAcceptanceStatusScraping;
import wiseSWlife.wiseSWlife.service.login.loginServiceInterface.LoginService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 성적 발표일 자정에 성적과 졸업평가를 스크래핑하여 데이터베이스에 저장하는 코드
 * 문제 : 년도별 성적 발표일시가 일정하지 않아 수정이 필요해보임
 */
@Service
@RequiredArgsConstructor
public class GraduationScheduler {

    private final IntranetRepository intranetRepository;
    private final LoginService loginService;
    private final EnumMapperFactory enumMapperFactory;
    private final Condition condition;
    private final ExamScraping examScraping;
    private final ExamRepository examRepository;
    private final TotalAcceptanceStatusScraping totalAcceptanceStatusScraping;
    private final MajorRepository majorRepository;

    /**
     * 1학기 성적 확인 일정
     * 22년도 1학기 최종 성적 발표일시(2022년 7월 15일(금))
     *
     * 시험 스크래핑 작업
     *
     * 1. Login_TB의 모든 ID, PW를 가져온다.
     *
     * 2. ID, PW의 반복문
     *
     *      2-1. Login scraping 수행(loginForm 추출)
     *      2-2. Exam scraping 수행
     *      2-3. Exam_TB에 저장
     */
    @Scheduled(cron = "0 0 0 15 7 ?")
    public void test() throws IOException, InterruptedException {
        System.out.println("현재 시간은 " + new Date());

        List<Intranet> intranets = intranetRepository.findAll();

        for(Intranet user : intranets){
            Member loginMember = loginService.login(user.getIntranetId(), user.getIntranetPw());

            if(loginMember.getSid().substring(4,7).equals("070")){
                continue;
            }
            String intCookie = loginMember.getIntCookie();
            String sid = loginMember.getSid();
            String groupName = loginMember.getMajor().charAt(0) + loginMember.getSid().substring(2,4);

            GraduationConditionEnumMapperValue condition = null;

            List<GraduationConditionEnumMapperValue> list = enumMapperFactory.get("GraduationCondition");
            for(GraduationConditionEnumMapperValue i : list){
                if(Objects.equals(i.getCode(), groupName)){
                    condition = i;
                    break;
                }
            }

            //졸업 시험 테이블 추출
            ExamTable examTable = examScraping.scraping(intCookie);
            ExamForm examForm = examScraping.convert(sid, examTable);

            examRepository.update(examForm);

            //전체 이수 현황 테이블 추출
            TotalAcceptanceStatusTable totalAcceptanceStatusTable = totalAcceptanceStatusScraping.scrapping(loginMember.getIntCookie());

            MajorForm majorForm = this.condition.checkMajor(sid, totalAcceptanceStatusTable.getBody().get("전공기초"), totalAcceptanceStatusTable.getBody().get("전공선택"), totalAcceptanceStatusTable.getBody().get("전공필수"));
            majorRepository.update(majorForm);
        }


    }



    //2학기 성적 확인 일정
    //21년도 2학기 최종 성적 발표일시(2022년 1월 7일(금))
    @Scheduled(cron = "0 0 0 7 1 ?")
    public void test2(){
        System.out.println("현재 시간은 " + new Date());
    }
}
