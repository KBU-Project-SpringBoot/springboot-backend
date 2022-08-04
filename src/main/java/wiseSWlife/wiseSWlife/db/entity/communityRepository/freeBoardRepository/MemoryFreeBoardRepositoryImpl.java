package wiseSWlife.wiseSWlife.db.entity.communityRepository.freeBoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.model.community.freeBoard.FreeBoard;
import wiseSWlife.wiseSWlife.db.repository.communityRepository.freeBoardRepository.FreeBoardCommentRepository;
import wiseSWlife.wiseSWlife.db.repository.communityRepository.freeBoardRepository.FreeBoardRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemoryFreeBoardRepositoryImpl implements FreeBoardRepository {

    private final FreeBoardCommentRepository commentRepository;

    private static Map<Long, FreeBoard> store = new ConcurrentHashMap<>();
    private static long freeBoardSeq = 0L;

    @Override
    public FreeBoard save(FreeBoard freeBoard) {

        freeBoard.setFreeBoardSeq(freeBoardSeq++);
        freeBoard.setDate(new Date());
        store.put(freeBoard.getFreeBoardSeq(), freeBoard);
        log.info("freeBoard save = {}",freeBoard);
        return freeBoard;
    }

    @Override
    public List<FreeBoard> findAllFreeBoard() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<FreeBoard> findFreeBoardBySid(String sid) {
        return findAllFreeBoard().stream()
                .filter(f -> f.getFreeBoardSid().equals(sid))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FreeBoard> findFreeBoardByFreeBoardSeq(Long freeBoardSeq) {
        if(store.containsKey(freeBoardSeq)){
            return Optional.of(store.get(freeBoardSeq));
        }
        return Optional.empty();
    }

    @Override
    public FreeBoard updateFreeBoard(Long freeBoardSeq, FreeBoard updatedFreeBoard) {
        Optional<FreeBoard> optionalFreeBoard = findFreeBoardByFreeBoardSeq(freeBoardSeq);
        if(optionalFreeBoard.isEmpty()){
            return null;
        }
        FreeBoard freeBoard = optionalFreeBoard.get();
        freeBoard.setFreeBoardContent(updatedFreeBoard.getFreeBoardContent());
        freeBoard.setFreeBoardText(updatedFreeBoard.getFreeBoardText());
        freeBoard.setFreeBoardTitle(updatedFreeBoard.getFreeBoardTitle());
        freeBoard.setFreeBoardNickName(updatedFreeBoard.getFreeBoardNickName());
        return freeBoard;
    }

    @Override
    public void removeFreeBoardByFreeBoardSeq(Long freeBoardSeq) {
        commentRepository.removeAllFreeBoardCommentByFreeBoardSeq(freeBoardSeq);
        store.remove(freeBoardSeq);
    }
}
