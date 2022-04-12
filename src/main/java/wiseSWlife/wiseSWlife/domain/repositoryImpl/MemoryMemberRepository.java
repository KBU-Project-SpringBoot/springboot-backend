package wiseSWlife.wiseSWlife.domain.repositoryImpl;

import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.domain.member.Member;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.MemberRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store = new ConcurrentHashMap<>();
    private static long manageSequence = 0L;

    private static Set<String> idStore = new HashSet<>();

    @Override
    public Member save(Member member) {
        member.setManageSeq(manageSequence++);
        store.put(member.getManageSeq(), member);

        //중복 확인용
        idStore.add(member.getLoginId());
        return member;
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    //Testing clear
    @Override
    public void clearMemStore() {
        store.clear();
    }
}
