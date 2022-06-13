package wiseSWlife.wiseSWlife.domain.repositoryImpl.communityRepository.freeBoardRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.domain.community.freeBoard.FreeBoardComment;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.freeBoardRepository.FreeBoardCommentRepository;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MemoryFreeBoardCommentRepositoryImpl implements FreeBoardCommentRepository{


    private static Map<Long, FreeBoardComment> store = new ConcurrentHashMap<>();
    private static long freeBoardCommentSeq = 0L;

    @Override
    // 자유 게시판 댓글 저장 (C)
    public FreeBoardComment save(FreeBoardComment freeBoardComment) {
        freeBoardComment.setFreeBoardCommentSeq(freeBoardCommentSeq++);
        store.put(freeBoardComment.getFreeBoardCommentSeq(), freeBoardComment);
        return freeBoardComment;
    }


    // 자유 게시판 관리번호로 연결된 모든 댓글 찾기 (R)
    @Override
    public List<FreeBoardComment> findAllFreeBoardCommentByFreeBoardSeq(Long freeBoardSeq) {
        return store.values().stream()
                .filter(c -> c.getFreeBoardSeq().equals(freeBoardSeq))
                .collect(Collectors.toList());
    }


    // 댓글 작성자가 작성한 모든 댓글 확인 (R)
    @Override
    public List<FreeBoardComment> findAllFreeBoardCommentBySid(String sid) {
        return store.values().stream()
                .filter(c -> c.getFreeBoardCommentSid().equals(sid))
                .collect(Collectors.toList());
    }

    @Override
    // 댓글 관리번호로 댓글 확인 (R)
    public Optional<FreeBoardComment> findFreeBoardCommentByFreeBoardCommentSeq(Long freeBoardCommentSeq) {
        if (!store.containsKey(freeBoardCommentSeq)) {
            return Optional.empty();
        }
        return Optional.of(store.get(freeBoardCommentSeq));

    }

    @Override
    // 댓글 업데이트 (U)
    public FreeBoardComment updateFreeBoardComment(Long freeBoardCommentSeq, FreeBoardComment updatedFreeBoardComment) {
        Optional<FreeBoardComment> freeBoardCommentByFreeBoardCommentSeq = findFreeBoardCommentByFreeBoardCommentSeq(freeBoardCommentSeq);
        if (freeBoardCommentByFreeBoardCommentSeq.isEmpty()) {
            return null;
        }
        FreeBoardComment freeBoardComment = freeBoardCommentByFreeBoardCommentSeq.get();
        freeBoardComment.setFreeBoardCommentSid(updatedFreeBoardComment.getFreeBoardCommentSid());
        freeBoardComment.setFreeBoardCommentNickName(updatedFreeBoardComment.getFreeBoardCommentNickName());
        freeBoardComment.setFreeBoardComment(updatedFreeBoardComment.getFreeBoardComment());
        freeBoardComment.setFreeBoardCommentContent(updatedFreeBoardComment.getFreeBoardCommentContent());
        return freeBoardComment;
    }

    @Override
    // 관리번호로 댓글 삭제 (D)
    public void removeFreeBoardCommentByFreeBoardCommentSeq(Long freeBoardCommentSeq) {
        if (store.containsKey(freeBoardCommentSeq)) {
            store.remove(freeBoardCommentSeq);
        }
    }

    @Override
    // 게시판 관리번호로 연관된 모든 댓글 삭제 (D)
    public void removeAllFreeBoardCommentByFreeBoardSeq(Long freeBoardSeq) {
        List<FreeBoardComment> list = findAllFreeBoardCommentByFreeBoardSeq(freeBoardSeq);
        for(FreeBoardComment comment : list)
            store.remove(comment.getFreeBoardCommentSeq());
    }
}
