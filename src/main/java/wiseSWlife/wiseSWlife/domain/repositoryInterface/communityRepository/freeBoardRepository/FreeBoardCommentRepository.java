package wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.freeBoardRepository;

import wiseSWlife.wiseSWlife.domain.community.freeBoard.FreeBoardComment;

import java.util.List;
import java.util.Optional;

public interface FreeBoardCommentRepository {

    // 자유 게시판 댓글 저장 (C)
    FreeBoardComment save(FreeBoardComment freeBoardComment);

    // 자유 게시판 관리번호로 연결된 모든 댓글 찾기 (R)
    List<FreeBoardComment> findAllFreeBoardCommentByFreeBoardSeq(Long freeBoardSeq);

    // 댓글 작성자가 작성한 모든 댓글 확인 (R)
    List<FreeBoardComment> findAllFreeBoardCommentByLoginId(String loginId);

    // 댓글 관리번호로 댓글 확인 (R)
    Optional<FreeBoardComment> findFreeBoardCommentByFreeBoardCommentSeq(Long freeBoardCommentSeq);

    // 댓글 업데이트 (U)
    FreeBoardComment updateFreeBoardComment(Long freeBoardCommentSeq, FreeBoardComment updatedFreeBoardComment);

    // 관리번호로 댓글 삭제 (D)
    void removeFreeBoardCommentByFreeBoardCommentSeq(Long freeBoardSeq);

    // 게시판 관리번호로 연관된 모든 댓글 삭제 (D)
    void removeAllFreeBoardCommentByFreeBoardSeq(Long freeBoardSeq);


}
