package wiseSWlife.wiseSWlife.db.repository.communityRepository.freeBoardRepository;

import wiseSWlife.wiseSWlife.dto.community.freeBoard.FreeBoard;

import java.util.List;
import java.util.Optional;

public interface FreeBoardRepository {

    // 게시물 저장 (C)
    FreeBoard save(FreeBoard freeBoard);

    // 모든 자유 게시판 확인 (R)
    List<FreeBoard> findAllFreeBoard();

    // 작성자가 작성한 게시글 확인 (R)
    List<FreeBoard> findFreeBoardBySid(String sid);

    //게시판 관리 번호를 통해 게시글 확인 (R)
    Optional<FreeBoard> findFreeBoardByFreeBoardSeq(Long freeBoardSeq);

    // 관리번호로 게시글 업데이트 (U)
    FreeBoard updateFreeBoard(Long freeBoardSeq, FreeBoard updatedFreeBoard);

    // 관리번호로 게시글 삭제(D)
    void removeFreeBoardByFreeBoardSeq(Long freeBoardSeq);
}
