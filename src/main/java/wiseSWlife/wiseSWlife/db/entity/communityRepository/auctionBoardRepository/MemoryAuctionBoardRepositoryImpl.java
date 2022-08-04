package wiseSWlife.wiseSWlife.db.entity.communityRepository.auctionBoardRepository;

import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.model.community.auction.Auction;
import wiseSWlife.wiseSWlife.db.repository.communityRepository.auctionBoardRepository.AuctionBoardRepository;

import java.util.ArrayList;
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
    public List<Auction> findAllAuction() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Auction> findAuctionBySeq(Long auctionSeq) {
        if(store.containsKey(auctionSeq)){
            return Optional.of(store.get(auctionSeq));
        }
        return Optional.empty();
    }

    @Override
    public List<Auction> findAllAuctionBySid(String sid) {
        return findAllAuction().stream()
                .filter(a -> a.getSellerSid().equals(sid))
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
