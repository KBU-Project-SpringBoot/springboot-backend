package wiseSWlife.wiseSWlife.domain.member;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import wiseSWlife.wiseSWlife.domain.authority.Authority;

@Data @Getter @Setter
public class Member {
    /** 회원 객체 **/

    private long manageSeq;

    private String loginId;
    private String password;

    private Authority authority;

    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;

    }
}
