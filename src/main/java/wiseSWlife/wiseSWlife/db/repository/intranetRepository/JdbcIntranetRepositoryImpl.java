package wiseSWlife.wiseSWlife.db.repository.intranetRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.dto.intranet.Intranet;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcIntranetRepositoryImpl implements IntranetRepository {

    private final DataSource dataSource;

    @Override
    public Intranet save(Intranet intranet) {

        String sql = "insert into Login_TB values(?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, intranet.getSid());
            pstmt.setString(2, intranet.getIntranetId());
            pstmt.setString(3, intranet.getIntranetPw());

            pstmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(conn, pstmt, rs);
        }
        return intranet;
    }

    @Override
    public Intranet update(Intranet intranet) {

        String sql = "update Login_TB set student_id = ?, intranet_id = ?, intranet_pw = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, intranet.getSid());
            pstmt.setString(2, intranet.getIntranetId());
            pstmt.setString(3, intranet.getIntranetPw());

            pstmt.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }

    @Override
    public Optional<Intranet> findByIntranetId(String intranetId) {
        String sql = "select * from Login_TB where intranet_id =?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, intranetId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                Intranet intranet = new Intranet();
                intranet.setSid(rs.getString("student_id"));
                intranet.setIntranetId(rs.getString("intranet_id"));
                intranet.setIntranetPw(rs.getString("intranet_pw"));
                return Optional.of(intranet);
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }

        return Optional.empty();
    }

    @Override
    public List<Intranet> findAll() {

        String sql = "select * from Login_TB";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<Intranet> intranets = new ArrayList<>();

            while (rs.next()){
                Intranet intranet = new Intranet();
                intranet.setSid(rs.getString("student_id"));
                intranet.setIntranetId(rs.getString("intranet_id"));
                intranet.setIntranetPw(rs.getString("intranet_pw"));
                intranets.add(intranet);
            }
            return intranets;

        } catch (Exception e){
            e.printStackTrace();
        }finally {
            close(conn, pstmt, rs);
        }
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
