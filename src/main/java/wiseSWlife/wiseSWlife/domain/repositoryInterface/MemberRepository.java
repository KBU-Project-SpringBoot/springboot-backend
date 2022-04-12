package wiseSWlife.wiseSWlife.domain.repositoryInterface;

import wiseSWlife.wiseSWlife.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    //회원 저장 (C)
    Member save(Member member);

    // LoginId로 회원 조회 (R)
    Optional<Member> findByLoginId(String loginId);

    //모든 회원 조회 (R)
    List<Member> findAll();



    // ManageSeq 이용시 사용
    default Optional<Member> findByManageSeq(Long manageSeq){
        return null; }

    // test 용도
    default void clearMemStore(){}


    //중복 확인 용도
    default List<String>allLoginId(){
        return null;
    }

    default List<String>allNickName(){
        return null;
    }
}
