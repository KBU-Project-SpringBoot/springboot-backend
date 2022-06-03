package wiseSWlife.wiseSWlife.domain.login.loginServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiseSWlife.wiseSWlife.domain.login.loginServiceInterface.LoginService;
import wiseSWlife.wiseSWlife.domain.member.Member;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.memberRepository.MemberRepository;

@Service
@RequiredArgsConstructor
public class SimpleLoginService implements LoginService {
    private final MemberRepository memberRepository;

    @Override
    public Member login(String loginId, String password){
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
