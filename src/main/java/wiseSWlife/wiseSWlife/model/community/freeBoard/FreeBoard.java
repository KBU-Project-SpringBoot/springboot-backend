package wiseSWlife.wiseSWlife.model.community.freeBoard;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class FreeBoard {
    /**
     * ==자유 게시판==
     *
     * 게시판 관리 번호
     * 작성자 학번
     * 작성자 닉네임 ( 로그인Id 사용 Or 익명 )
     * 날짜
     * 제목
     * 게시판 작성 내용
     * content ( 파일, 이미지...etc) ..향후 확장 가능
     */

    private Long freeBoardSeq;

    private String freeBoardSid;

    private String freeBoardNickName;

    private Date date;

    private String freeBoardTitle;

    private String freeBoardText;

    private Object freeBoardContent;

    public FreeBoard(String freeBoardSid, String freeBoardNickName, String freeBoardTitle, String freeBoardText) {
        this.freeBoardSid = freeBoardSid;
        this.freeBoardNickName = freeBoardNickName;

        this.freeBoardTitle = freeBoardTitle;
        this.freeBoardText = freeBoardText;
    }
}
