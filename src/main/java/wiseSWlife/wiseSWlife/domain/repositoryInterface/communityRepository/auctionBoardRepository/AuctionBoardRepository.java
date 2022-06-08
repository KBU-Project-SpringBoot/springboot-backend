package wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.auctionBoardRepository;

import wiseSWlife.wiseSWlife.domain.community.auction.Auction;

import java.util.List;
import java.util.Optional;

public interface AuctionBoardRepository {

    // save
    // findAllAuctionByloginId

    // 상품 등록
    Auction save(Auction auction);

    //모든 상품 찾기
    List<Auction> findAllAuction();

    //상품 번호로 상품 찾기
    Optional<Auction> findAuctionBySeq(Long auctionSeq);
    // 로그인 Id로 등록한 모든 상품 확인
    List<Auction> findAllAuctionBySid(String sid);
    // 판매 완료 확인
    Boolean soldOutCheck(Long auctionSeq);
    // 상품 업데이트
    Auction updateAuction(Auction auction);
    // 상품 제거
    void removeAuction(Long auctionSeq);

    // 상품 판매 완료
    Auction soldOut(Long auctionSeq);




}
