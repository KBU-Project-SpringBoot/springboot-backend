package wiseSWlife.wiseSWlife.domain.login.loginServiceInterface;

import wiseSWlife.wiseSWlife.domain.member.Member;

import java.io.IOException;

public interface LoginService {
    Member login(String loginId, String password) throws IOException, InterruptedException;
}
