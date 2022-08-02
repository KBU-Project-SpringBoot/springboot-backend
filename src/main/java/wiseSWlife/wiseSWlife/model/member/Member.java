package wiseSWlife.wiseSWlife.model.member;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class Member {
    /** 인트라넷 회원 정보 객체 **/

    private String sid;//학번
    private String name;//이름
    private String major;//학과

    private String intCookie;//map=> key : 컴소생황 sessionkey, value : 인트라넷 쿠키값

    public Member(String sid, String name, String major, String intCookie) {
        this.sid = sid;
        this.name = name;
        this.major = major;
        this.intCookie = intCookie;
    }
}
