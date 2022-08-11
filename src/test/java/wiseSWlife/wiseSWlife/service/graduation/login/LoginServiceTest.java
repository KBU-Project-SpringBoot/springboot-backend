package wiseSWlife.wiseSWlife.service.graduation.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wiseSWlife.wiseSWlife.db.entity.memberRepository.MemoryMemberRepository;
import wiseSWlife.wiseSWlife.db.repository.memberRepository.MemberRepository;
import wiseSWlife.wiseSWlife.model.member.Member;
import wiseSWlife.wiseSWlife.service.login.loginServiceImpl.SimpleLoginService;

import static org.assertj.core.api.Assertions.*;
import java.io.IOException;

public class LoginServiceTest {
    MemberRepository memberRepository = new MemoryMemberRepository();
    SimpleLoginService simpleLoginService = new SimpleLoginService(memberRepository);

    @DisplayName("로그인 여부")
    @Test
    void LoginTest() throws IOException, InterruptedException {
        //given

        //when
        Member member = simpleLoginService.login("아이디", "비밀번호");

        //then
        assertThat(member).isNotNull();
    }


}
