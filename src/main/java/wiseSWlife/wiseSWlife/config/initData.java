package wiseSWlife.wiseSWlife.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wiseSWlife.wiseSWlife.domain.community.auction.Auction;
import wiseSWlife.wiseSWlife.domain.community.freeBoard.FreeBoard;
import wiseSWlife.wiseSWlife.domain.member.Member;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.auctionBoardRepository.AuctionBoardRepository;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.freeBoardRepository.FreeBoardRepository;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.memberRepository.MemberRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class initData {
    private final MemberRepository memberRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final AuctionBoardRepository auctionBoardRepository;

    @PostConstruct
    public void init(){
            
        //init Member
        memberRepository.save(new Member("201704036", "신범철", "컴퓨터소프트웨어", ""));
        memberRepository.save(new Member("201604009", "김지용", "컴퓨터소프트웨어",""));

        // init community freeBoard
        freeBoardRepository.save(new FreeBoard("201604009", "익명","영졸 기간이 언제인가요?", "영졸 시작 기간은?"));

        //init community auctionBoard
        auctionBoardRepository.save(new Auction("김지용", "201604009","소주", 12000L, "아끼던거 팜", null));
    }
}
