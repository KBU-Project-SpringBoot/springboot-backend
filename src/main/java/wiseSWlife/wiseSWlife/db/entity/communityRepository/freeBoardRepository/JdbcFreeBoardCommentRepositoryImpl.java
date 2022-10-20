package wiseSWlife.wiseSWlife.db.entity.communityRepository.freeBoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.db.repository.communityRepository.freeBoardRepository.FreeBoardCommentRepository;
import wiseSWlife.wiseSWlife.model.community.freeBoard.FreeBoardComment;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
//@Primary
//@Repository
@RequiredArgsConstructor
public class JdbcFreeBoardCommentRepositoryImpl implements FreeBoardCommentRepository {

    private final DataSource dataSource;

    @Override
    public FreeBoardComment save(FreeBoardComment freeBoardComment) {

        String sql = "insert into Freeboard_comment_TB(freeboard_comment_nickname, freeboard_comment, freeboard_sequence, student_id) values( ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,freeBoardComment.getFreeBoardCommentNickName());
            pstmt.setString(2,freeBoardComment.getFreeBoardComment());
            pstmt.setLong(3,freeBoardComment.getFreeBoardSeq());
            pstmt.setString(4,freeBoardComment.getFreeBoardCommentSid());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                freeBoardComment.setFreeBoardSeq(rs.getLong(1));
                return freeBoardComment;
            }

        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }

        return null;
    }

    @Override
    public List<FreeBoardComment> findAllFreeBoardCommentByFreeBoardSeq(Long freeBoardSeq) {
        String sql = "select * from Freeboard_comment_TB where freeboard_sequence = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FreeBoardComment> commentList = new ArrayList<>();
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, freeBoardSeq);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                FreeBoardComment freeBoardComment = new FreeBoardComment(rs.getString("student_id"), rs.getLong("freeboard_sequence"), rs.getString("freeboard_comment_nickname"), rs.getString("freeboard_comment"));
                freeBoardComment.setFreeBoardCommentSeq(rs.getLong("freeboard_comment_sequence"));
                commentList.add(freeBoardComment);
            }
            return commentList;


        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }

        return null;
    }

    @Override
    public List<FreeBoardComment> findAllFreeBoardCommentBySid(String sid) {

        String sql = "select * from Freeboard_comment_TB where student_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FreeBoardComment> commentList = new ArrayList<>();
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, sid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                FreeBoardComment freeBoardComment = new FreeBoardComment(rs.getString("student_id"), rs.getLong("freeboard_sequence"), rs.getString("freeboard_comment_nickname"), rs.getString("freeboard_comment"));
                freeBoardComment.setFreeBoardCommentSeq(rs.getLong("freeboard_comment_sequence"));
                commentList.add(freeBoardComment);
            }
            return commentList;


        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }

        return null;

    }

    @Override
    public Optional<FreeBoardComment> findFreeBoardCommentByFreeBoardCommentSeq(Long freeBoardCommentSeq) {
        String sql = "select * from Freeboard_comment_TB where freeboard_comment_sequence = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,freeBoardCommentSeq);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                FreeBoardComment freeBoardComment = new FreeBoardComment(rs.getString("student_id"), rs.getLong("freeboard_sequence"), rs.getString("freeboard_comment_nickname"), rs.getString("freeboard_comment"));
                freeBoardComment.setFreeBoardCommentSeq(rs.getLong("freeboard_comment_sequence"));
                return Optional.of(freeBoardComment);
            }
        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }

        return Optional.empty();
    }

    @Override
    public FreeBoardComment updateFreeBoardComment(Long freeBoardCommentSeq, FreeBoardComment updatedFreeBoardComment) {
        String sql = "update Freeboard_comment_TB set freeboard_comment_nickname =?, freeboard_comment = ?, freeboard_sequence = ?, student_id = ? where freeboard_comment_sequence = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,updatedFreeBoardComment.getFreeBoardCommentNickName());
            pstmt.setString(2,updatedFreeBoardComment.getFreeBoardComment());
            pstmt.setLong(3, updatedFreeBoardComment.getFreeBoardSeq());
            pstmt.setString(4,updatedFreeBoardComment.getFreeBoardCommentSid());
            pstmt.setLong(5,freeBoardCommentSeq);

            pstmt.executeUpdate();


        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }

        return updatedFreeBoardComment;
    }

    @Override
    public void removeFreeBoardCommentByFreeBoardCommentSeq(Long freeBoardCommentSeq) {
        String sql = "delete from Freeboard_TB where freeboard_comment_sequence =?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if(!findFreeBoardCommentByFreeBoardCommentSeq(freeBoardCommentSeq).isEmpty()){
            try{
                conn = getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setLong(1,freeBoardCommentSeq);
                pstmt.executeUpdate();
            }catch (Exception e){
                log.warn("Exception = {}",e);
            }finally {
                close(conn,pstmt,rs);
            }
        }
    }

    @Override
    public void removeAllFreeBoardCommentByFreeBoardSeq(Long freeBoardSeq) {
        // 자동 삭제됨
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
