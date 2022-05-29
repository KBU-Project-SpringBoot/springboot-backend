package wiseSWlife.wiseSWlife.domain.repositoryImpl.communityRepository.auctionBoardRepository;

import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.domain.auction.Auction;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.auctionBoardRepository.AuctionBoardRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryAuctionBoardRepositoryImpl implements AuctionBoardRepository {

    private Map<Long, Auction> store = new ConcurrentHashMap<>();

    private static Long auctionSeq = 0L;

    @Override
    public Auction save(Auction auction) {
        auction.setAuctionSeq(auctionSeq++);
        store.put(auction.getAuctionSeq(), auction);
        return auction;
    }

    @Override
    public Optional<Auction> findAuctionBySeq(Long auctionSeq) {
        if(store.containsKey(auctionSeq)){
            return Optional.of(store.get(auctionSeq));
        }
        return Optional.empty();
    }

    @Override
    public List<Auction> findAllAuctionByLoginId(String loginId) {
        return store.values().stream()
                .filter(a -> a.getSeller().equals(loginId))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean soldOutCheck(Long auctionSeq) {
        Optional<Auction> findAuction = findAuctionBySeq(auctionSeq);
        if(findAuction.isEmpty()){
            return false;
        }
        return findAuction.get().isSoldOut();
    }

    @Override
    public Auction soldOut(Long auctionSeq) {
        Optional<Auction> findAuction = findAuctionBySeq(auctionSeq);
        if(findAuction.isEmpty()){
            return null;
        }
        findAuction.get().setSoldOut(true);
        return findAuction.get();
    }

    @Override
    public Auction updateAuction(Auction auction) {
        Optional<Auction> findAuction = findAuctionBySeq(auction.getAuctionSeq());
        if(findAuction.isEmpty()){
            return null;
        }
        Auction pastAuction = findAuction.get();
        pastAuction.setDate(auction.getDate());
        pastAuction.setImgUrl(auction.getImgUrl());
        pastAuction.setPrice(auction.getPrice());
        pastAuction.setProductName(auction.getProductName());
        pastAuction.setText(auction.getText());
        pastAuction.setSoldOut(auction.isSoldOut());

        return pastAuction;
    }

    @Override
    public void removeAuction(Long auctionSeq) {
        Optional<Auction> findAuction = findAuctionBySeq(auctionSeq);
        if (!findAuction.isEmpty()){
            store.remove(auctionSeq);
        }
    }


}