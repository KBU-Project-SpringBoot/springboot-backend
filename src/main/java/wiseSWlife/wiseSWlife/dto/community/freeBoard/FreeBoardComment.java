package wiseSWlife.wiseSWlife.dto.community.freeBoard;

import lombok.Data;

@Data
public class FreeBoardComment {
    /**
     * ==댓글==
     *
     * 자유 게시판 관리 번호
     * 댓글 관리 번호
     * 댓글 작성자 id
     * 댓글 작성자 닉네임 ( 로그인Id 사용 Or 익명 )
     * 날짜
     * 댓글
     * content ( 파일, 이미지...etc) ..향후 확장 가능
     */

    private Long freeBoardSeq;

    private Long freeBoardCommentSeq;

    private String freeBoardCommentSid;

    private String freeBoardCommentNickName;



    public FreeBoardComment(String freeBoardCommentSid, Long freeBoardSeq, String freeBoardCommentNickName, String freeBoardComment) {
        this.freeBoardCommentSid = freeBoardCommentSid;
        this.freeBoardCommentNickName = freeBoardCommentNickName;
        this.freeBoardSeq = freeBoardSeq;
        this.freeBoardComment = freeBoardComment;
    }

    private String freeBoardComment;

    private Object freeBoardCommentContent;
}
