package wiseSWlife.wiseSWlife.domain.repositoryImpl.memberRepository;

import org.springframework.stereotype.Repository;

import wiseSWlife.wiseSWlife.domain.member.Member;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.memberRepository.MemberRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryMemberRepository implements MemberRepository {
    private static Map<String, Member> store = new ConcurrentHashMap<>();

    @Override
    public Member save(Member member) {
        store.put(member.getSid(), member);
        return member;
    }

    @Override
    public Member update(Member member){
        Member findMember = store.get(member.getSid());

        findMember.setIntCookie(member.getSid());

        return findMember;
    }

    @Override
    public Optional<Member> findBySid(String sid){
        return findAll().stream()
                .filter(m->m.getSid().equals(sid))
                .findFirst();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

}
