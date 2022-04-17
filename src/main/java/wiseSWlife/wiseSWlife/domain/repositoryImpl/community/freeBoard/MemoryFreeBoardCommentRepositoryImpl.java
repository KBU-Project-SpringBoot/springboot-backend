package wiseSWlife.wiseSWlife.domain.repositoryImpl.community.freeBoard;

import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.domain.community.freeBoard.FreeBoardComment;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.freeBoardRepository.FreeBoardCommentRepository;

import java.util.List;

@Repository
public class MemoryFreeBoardCommentRepositoryImpl implements FreeBoardCommentRepository{

    @Override
    public FreeBoardComment save(FreeBoardComment freeBoardComment) {
        return null;
    }

    @Override
    public List<FreeBoardComment> findAllFreeBoardCommentByFreeBoardSeq(Long freeBoardSeq) {
        return null;
    }

    @Override
    public List<FreeBoardComment> findAllFreeBoardCommentByLoginId(String loginId) {
        return null;
    }

    @Override
    public FreeBoardComment findFreeBoardCommentByFreeBoardCommentSeq(Long freeBoardCommentSeq) {
        return null;
    }

    @Override
    public FreeBoardComment updateFreeBoardComment(Long freeBoardCommentSeq, FreeBoardComment updatedFreeBoardComment) {
        return null;
    }

    @Override
    public void removeFreeBoardCommentByFreeBoardCommentSeq(Long freeBoardSeq) {

    }

    @Override
    public void removeAllFreeBoardCommentByFreeBoardSeq(Long freeBoardSeq) {

    }
}
