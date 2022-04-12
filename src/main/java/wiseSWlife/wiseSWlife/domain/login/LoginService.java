package wiseSWlife.wiseSWlife.domain.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.domain.member.Member;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member login(String loginId, String password){
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
