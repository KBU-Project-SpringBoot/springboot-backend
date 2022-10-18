package wiseSWlife.wiseSWlife.db.repository.memberRepository;

import wiseSWlife.wiseSWlife.model.member.Member;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface MemberRepository {
    //회원 저장 (C)
    Member save(Member member) throws SQLException;

    Member update(Member member);

    // sid로 회원 조회 (R)
    Optional<Member> findBySid(String sid);

    //모든 회원 조회 (R)
    List<Member> findAll();

}
