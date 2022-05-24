package wiseSWlife.wiseSWlife.domain.community.freeBoard;

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
     * 작성자 id
     * 작성자 닉네임 ( 로그인Id 사용 Or 익명 )
     * 날짜
     * 제목
     * 게시판 작성 내용
     * content ( 파일, 이미지...etc) ..향후 확장 가능
     */

    private Long freeBoardSeq;

    private String freeBoardLoginId;

    private String freeBoardNickName;



    private String freeBoardTitle;

    private String freeBoardText;

    private Object freeBoardContent;

    public FreeBoard(String freeBoardLoginId, String freeBoardNickName, Date date, String freeBoardTitle, String freeBoardText) {
        this.freeBoardLoginId = freeBoardLoginId;
        this.freeBoardNickName = freeBoardNickName;

        this.freeBoardTitle = freeBoardTitle;
        this.freeBoardText = freeBoardText;
    }
}
