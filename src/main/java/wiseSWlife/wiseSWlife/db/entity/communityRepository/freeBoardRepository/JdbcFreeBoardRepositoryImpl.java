package wiseSWlife.wiseSWlife.db.entity.communityRepository.freeBoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.db.repository.communityRepository.freeBoardRepository.FreeBoardRepository;
import wiseSWlife.wiseSWlife.model.community.freeBoard.FreeBoard;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class JdbcFreeBoardRepositoryImpl implements FreeBoardRepository {

    private final DataSource dataSource;

    @Override
    public FreeBoard save(FreeBoard freeBoard) {

        String sql = "insert into Freeboard_TB(student_id, student_nickname, freeboard_date, freeboard_title, freeboard_text) values(?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,freeBoard.getFreeBoardSid());
            pstmt.setString(2,freeBoard.getFreeBoardNickName());
            pstmt.setString(3, String.valueOf(freeBoard.getDate()));
            pstmt.setString(4,freeBoard.getFreeBoardTitle());
            pstmt.setString(5,freeBoard.getFreeBoardText());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                freeBoard.setFreeBoardSeq(rs.getLong("freeboard_sequence"));
                return freeBoard;
            }

        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }

        return null;

    }

    @Override
    public List<FreeBoard> findAllFreeBoard() {
        String sql = "select * from Freeboard_TB";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FreeBoard> freeBoardList = new ArrayList();
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if(rs.next()){
                FreeBoard freeBoard = new FreeBoard(rs.getString("student_id"), rs.getString("student_nickname"), rs.getString("freeboard_title"), rs.getString("freeboard_text"));
                freeBoard.setFreeBoardSeq(rs.getLong("freeboard_sequence"));
                freeBoardList.add(freeBoard);
            }

        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }
        return freeBoardList;
    }

    @Override
    public List<FreeBoard> findFreeBoardBySid(String sid) {
        String sql = "select * from Freeboard_TB where student_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FreeBoard> freeBoardList = new ArrayList();
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,sid);
            rs = pstmt.executeQuery();

            if(rs.next()){
                FreeBoard freeBoard = new FreeBoard(rs.getString("student_id"), rs.getString("student_nickname"), rs.getString("freeboard_title"), rs.getString("freeboard_text"));
                freeBoard.setFreeBoardSeq(rs.getLong("freeboard_sequence"));
                freeBoardList.add(freeBoard);
            }

        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }
        return freeBoardList;
    }

    @Override
    public Optional<FreeBoard> findFreeBoardByFreeBoardSeq(Long freeBoardSeq) {
        String sql = "select * from Freeboard_TB where freeboard_sequence = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,freeBoardSeq);
            rs = pstmt.executeQuery();

            if(rs.next()){
                FreeBoard freeBoard = new FreeBoard(rs.getString("student_id"), rs.getString("student_nickname"), rs.getString("freeboard_title"), rs.getString("freeboard_text"));
                freeBoard.setFreeBoardSeq(rs.getLong("freeboard_sequence"));
                return Optional.of(freeBoard);
            }

        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }

        return Optional.empty();
    }

    @Override
    public FreeBoard updateFreeBoard(Long freeBoardSeq, FreeBoard updatedFreeBoard) {
        String sql = "update Freeboard_TB set student_id = ?, student_nickname = ?, freeboard_date = ?, freeboard_title =? , freeboard_text = ? where freeboard_sequence = ? ";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if(findFreeBoardByFreeBoardSeq(freeBoardSeq).isEmpty()){
            return null;
        }
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,updatedFreeBoard.getFreeBoardSid());
            pstmt.setString(2,updatedFreeBoard.getFreeBoardNickName());
            pstmt.setString(3, String.valueOf(updatedFreeBoard.getDate()));
            pstmt.setString(4, updatedFreeBoard.getFreeBoardTitle());
            pstmt.setString(5, updatedFreeBoard.getFreeBoardText());
            pstmt.setLong(6, updatedFreeBoard.getFreeBoardSeq());
            pstmt.executeUpdate();

        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }

        return updatedFreeBoard;
    }

    @Override
    public void removeFreeBoardByFreeBoardSeq(Long freeBoardSeq) {
        String sql = "delete from Freeboard_TB where freeboard_sequence = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (!findFreeBoardByFreeBoardSeq(freeBoardSeq).isEmpty()) {
            try{
                conn = getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setLong(1,freeBoardSeq);
                pstmt.executeUpdate();
            }catch (Exception e){
                log.warn("Exception = {}",e);
            }finally {
                close(conn,pstmt,rs);
            }
        }

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
