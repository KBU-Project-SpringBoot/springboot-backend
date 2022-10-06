package wiseSWlife.wiseSWlife.model.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 인트라넷 회원 정보 객체
 * sid : 학번
 * name : 이름
 * major : 학과
 * intCookie : 인트라넷 쿠키값
 */
@Getter @Setter @AllArgsConstructor
public class Member {

    private String sid;
    private String name;
    private String major;

    private String intCookie;
}
