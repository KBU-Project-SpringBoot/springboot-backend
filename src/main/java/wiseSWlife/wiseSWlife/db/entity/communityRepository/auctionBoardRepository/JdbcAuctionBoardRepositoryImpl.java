package wiseSWlife.wiseSWlife.db.entity.communityRepository.auctionBoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.db.repository.communityRepository.auctionBoardRepository.AuctionBoardRepository;
import wiseSWlife.wiseSWlife.db.repository.imgRepository.ImgRepository;
import wiseSWlife.wiseSWlife.model.community.auction.Auction;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class JdbcAuctionBoardRepositoryImpl implements AuctionBoardRepository {

    private final DataSource dataSource;
    private final ImgRepository imgRepository;
    @Override
    public Auction save(Auction auction) {
        String sql = "insert into Auction_TB (student_id, auction_product_name, auction_price, auction_text, auction_date, image_sequence, seller) values(?, ?, ? ,?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,auction.getSellerSid());
            pstmt.setString(2,auction.getProductName());
            pstmt.setLong(3,auction.getPrice());
            pstmt.setString(4,auction.getText());
            pstmt.setString(5, String.valueOf(auction.getDate()));
            pstmt.setLong(6,imgRepository.findImgByUUID(auction.getImgUrl()).getManageSeq());
            pstmt.setString(7,auction.getSeller());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                auction.setAuctionSeq(rs.getLong(1));
            }

        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }

        return auction;
    }

    @Override
    public List<Auction> findAllAuction() {
        String sql = "select * from Auction_TB";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Auction> auctionList = new ArrayList<>();

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Auction auction = new Auction(
                        rs.getString("seller"),
                        rs.getString("student_id"),
                        rs.getString("auction_product_name"),
                        rs.getInt("auction_price"),
                        imgRepository.findImgBySeq(rs.getLong("image_sequence")).getStoreImgName(),
                        rs.getString("auction_text"),
                        rs.getString("auction_date")
                );
                auction.setAuctionSeq(rs.getLong("auction_sequence"));
                auctionList.add(auction);
            }

        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
         }

        return auctionList;
    }

    @Override
    public Optional<Auction> findAuctionBySeq(Long auctionSeq) {
        String sql = "select * from Auction_TB where auction_sequence = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,auctionSeq);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Auction auction = new Auction(
                        rs.getString("seller"),
                        rs.getString("student_id"),
                        rs.getString("auction_product_name"),
                        rs.getInt("auction_price"),
                        imgRepository.findImgBySeq(rs.getLong("image_sequence")).getStoreImgName(),
                        rs.getString("auction_text"),
                        rs.getString("auction_date")
                );
                auction.setAuctionSeq(rs.getLong("auction_sequence"));
                return Optional.of(auction);
            }

        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }

        return Optional.empty();
    }

    @Override
    public List<Auction> findAllAuctionBySid(String sid) {
        String sql = "select * from Auction_TB where student_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Auction> auctionList = new ArrayList<>();

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,sid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Auction auction = new Auction(
                        rs.getString("seller"),
                        rs.getString("student_id"),
                        rs.getString("auction_product_name"),
                        rs.getInt("auction_price"),
                        imgRepository.findImgBySeq(rs.getLong("image_sequence")).getStoreImgName(),
                        rs.getString("auction_text"),
                        rs.getString("auction_date")
                );
                auction.setAuctionSeq(rs.getLong("auction_sequence"));
                auctionList.add(auction);
            }

        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }

        return auctionList;
    }

    @Override
    public Boolean soldOutCheck(Long auctionSeq) {
        Optional<Auction> auction = findAuctionBySeq(auctionSeq);
        if(auction.isEmpty()){
            return null;
        }
        return auction.get().isSoldOut();
    }

    @Override
    public Auction updateAuction(Auction auction) {
        String sql = "update Auction_TB set student_id = ?, auction_product_name = ?, auction_price = ?, auction_text = ?, auction_date =?, auction_sold_out =? , image_sequence = ?, seller = ? where auction_sequence = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, auction.getSellerSid());
            pstmt.setString(2,auction.getProductName());
            pstmt.setLong(3, auction.getPrice());
            pstmt.setString(4,auction.getText());
            pstmt.setString(5,auction.getDate());
            pstmt.setString(6,String.valueOf(auction.isSoldOut()));
            pstmt.setLong(7,imgRepository.findImgByUUID(auction.getImgUrl()).getManageSeq());
            pstmt.setString(8,auction.getSeller());
            pstmt.setLong(9,auction.getAuctionSeq());
            pstmt.executeUpdate();

            log.info("ehla??");


        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }

        return auction;
    }

    @Override
    public void removeAuction(Long auctionSeq) {
        Optional<Auction> auction = findAuctionBySeq(auctionSeq);
        if(!auction.isEmpty()){
            String sql = "delete from Auction_TB where auction_sequence = ?";

            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try{
                conn = getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setLong(1, auctionSeq);
                pstmt.executeUpdate();
            }catch (Exception e){
                log.warn("Exception = {}",e);
            }finally {
                close(conn,pstmt,rs);
            }
        }
    }

    @Override
    public Auction soldOut(Long auctionSeq) {
        return null;
    }


    // DB connect
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    // DB close
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }

    // DB close
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (pstmt != null) {
                pstmt.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                close(conn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
