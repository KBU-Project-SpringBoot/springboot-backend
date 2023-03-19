package wiseSWlife.wiseSWlife.service.graduation.scrapingImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wiseSWlife.wiseSWlife.dto.member.Member;
import wiseSWlife.wiseSWlife.service.graduation.scraping.Exam;
import wiseSWlife.wiseSWlife.service.login.SimpleLoginService;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

//public class ExamTest {
//    SimpleLoginService simpleLoginService = new SimpleLoginService();
//
//    @DisplayName("로그인 여부")
//    @Test
//    void LoginTest() throws IOException, InterruptedException {
//        //given
//
//        //when
//        Member member = simpleLoginService.login("아이디", "비밀번호");
//
//        //then
//        assertThat(member).isNotNull();
//    }
//
//
//    @DisplayName("졸업 시험 합/불 테스트")
//    @Test
//    void examScrapingDataSetTest() throws IOException, InterruptedException {
//        //given
//        Member member = simpleLoginService.login("아이디", "비밀번호");
//        Exam exam = new Exam();
//
//        //when
//        Map<String, Boolean> testMap = (Map<String, Boolean>) exam.scraping(member.getIntCookie());
//
//        //then
//        assertThat(testMap.get("성경")).isFalse();
//
//    }
//}
