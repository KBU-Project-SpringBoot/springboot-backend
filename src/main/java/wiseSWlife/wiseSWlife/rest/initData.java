package wiseSWlife.wiseSWlife.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wiseSWlife.wiseSWlife.domain.member.Member;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.MemberRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class initData {
    private final MemberRepository memberRepository;
    
    @PostConstruct
    public void init(){
            
        //init Member
        memberRepository.save(new Member("김지용", "김지용"));
        memberRepository.save(new Member("신범철", "신범철"));
        
    }
}
