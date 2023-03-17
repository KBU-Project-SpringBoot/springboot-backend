package wiseSWlife.wiseSWlife.service.login;

import wiseSWlife.wiseSWlife.dto.member.Member;

import java.io.IOException;
import java.sql.SQLException;

public interface LoginService {
    Member login(String loginId, String password) throws IOException, InterruptedException, SQLException;
}
