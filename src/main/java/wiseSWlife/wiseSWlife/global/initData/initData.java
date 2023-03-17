//package wiseSWlife.wiseSWlife.global.initData;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import wiseSWlife.wiseSWlife.db.repository.imgRepository.ImgRepository;
//import wiseSWlife.wiseSWlife.dto.community.auction.Auction;
//import wiseSWlife.wiseSWlife.dto.community.freeBoard.FreeBoard;
//import wiseSWlife.wiseSWlife.dto.community.freeBoard.FreeBoardComment;
//import wiseSWlife.wiseSWlife.dto.img.ImgItem;
//import wiseSWlife.wiseSWlife.dto.member.Member;
//import wiseSWlife.wiseSWlife.db.repository.communityRepository.auctionBoardRepository.AuctionBoardRepository;
//import wiseSWlife.wiseSWlife.db.repository.communityRepository.freeBoardRepository.FreeBoardCommentRepository;
//import wiseSWlife.wiseSWlife.db.repository.communityRepository.freeBoardRepository.FreeBoardRepository;
//import wiseSWlife.wiseSWlife.db.repository.memberRepository.MemberRepository;
//import javax.annotation.PostConstruct;
//import java.util.Date;
//
//@Component
//@RequiredArgsConstructor
//public class initData {
//    private final MemberRepository memberRepository;
//    private final FreeBoardRepository freeBoardRepository;
//    private final FreeBoardCommentRepository freeBoardCommentRepository;
//    private final AuctionBoardRepository auctionBoardRepository;
//    private final ImgRepository imgRepository;
//
//    @PostConstruct
//    public void init(){
//
//        //init Member
//        memberRepository.save(new Member("201704036", "신범철", "컴퓨터소프트웨어", ""));
//        memberRepository.save(new Member("201604009", "김지용", "컴퓨터소프트웨어",""));
//
//        // init community freeBoard
//        freeBoardRepository.save(new FreeBoard("201704036", "컴소남","채플 일수 업데이트 언제되나요?",
//                "채플 일수가 아직 업데이트가 되지 않아 걱정되서 \n 혹시 언제 업데이트 되시는지 아시나요?"));
//        freeBoardRepository.save(new FreeBoard("201604009", "컴소인","영졸 기간이 언제인가요?",
//                "영졸 꼭 봐야지 졸업 가능한가요? \n 듣기로는 그 토익 점수로도 패스 가능하다던데... "));
//        freeBoardRepository.save(new FreeBoard("201604009", "익명","오늘 점심",
//                "오늘 학시 메뉴가 뭔가요? \n 별로면 점메추좀 부탁"));
//
//        //init community freeBoard Comment
////        freeBoardCommentRepository.save(new FreeBoardComment("201504009",0L,"익명",new Date(),"늦으면 3일 안에 업데이트 되요"));
////        freeBoardCommentRepository.save(new FreeBoardComment("201504009",0L,"학교 관계자",new Date(),"5일 걸린 다고 합니다."));
////        freeBoardCommentRepository.save(new FreeBoardComment("201504009",1L,"영포자",new Date(),"토익 몇점 이상이면 패스"));
////        freeBoardCommentRepository.save(new FreeBoardComment("201504009",1L,"영어 선생님",new Date(),"토익 550이면 통과 입니다."));
////        freeBoardCommentRepository.save(new FreeBoardComment("201504009",2L,"감자튀김",new Date(),"햄벅 추천"));
//
//
//        //init img save
//        ImgItem img1 = imgRepository.save(new ImgItem("웹교제","111.png"));
//        ImgItem img2 = imgRepository.save(new ImgItem("마우스","222.png"));
//        ImgItem img3 = imgRepository.save(new ImgItem("맥북","333.png"));
//        ImgItem img4 = imgRepository.save(new ImgItem("치약","444.png"));
//
//        //init community auctionBoard
//        auctionBoardRepository.save(new Auction("박수홍", "201304009","웹 시스템 개발 교재", 10000,img1.getStoreImgName(), "웹 시스템 교재 판매 합니다~", null));
//        auctionBoardRepository.save(new Auction("황종민", "201204009","내 절친 마우스", 17000,img2.getStoreImgName(), "아끼던거 팜", null));
//        auctionBoardRepository.save(new Auction("박희준", "201104009","맥북 판매", 0,img3.getStoreImgName(), "선 제시", null));
//        auctionBoardRepository.save(new Auction("김지용", "201004009","치약", 500, img4.getStoreImgName(),"딸기맛 치약 입니다", null));
//
//
//    }
//}
