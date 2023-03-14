package wiseSWlife.wiseSWlife.db.repository.bcrRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.dto.graduation.form.BCRForm;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@Repository
@Primary
@RequiredArgsConstructor
public class JdbcBCRRepositoryImpl implements BCRRepository {

    private final DataSource dataSource;

    @Override
    public BCRForm save(BCRForm bcrForm) {
        String sql = "insert into Basic_Common_Requirement_TB (student_id, chapel, conduction, wheat_grain, bible_study) values(?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bcrForm.getSid());
            pstmt.setInt(2,bcrForm.getChapel());
            pstmt.setInt(3,bcrForm.getConduction());
            pstmt.setBoolean(4,bcrForm.getWheatGrain());
            pstmt.setInt(5,bcrForm.getBibleStudy());

            pstmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(conn, pstmt, rs);
        }

        return bcrForm;
    }

    @Override
    public BCRForm update(BCRForm bcrForm) {
        String sql = "update Basic_Common_Requirement_TB set student_id = ?, chapel = ?, conduction = ?, wheat_grain = ?, bible_study = ? where student_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bcrForm.getSid());
            pstmt.setInt(2,bcrForm.getChapel());
            pstmt.setInt(3,bcrForm.getConduction());
            pstmt.setBoolean(4,bcrForm.getWheatGrain());
            pstmt.setInt(5,bcrForm.getBibleStudy());
            pstmt.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return bcrForm;
    }

    @Override
    public Optional<BCRForm> findBCRBySid(String sid) {
        String sql = "select * from Basic_Common_Requirement_TB where student_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sid);
            rs = pstmt.executeQuery();

            if(rs.next()){
                BCRForm bcrForm = new BCRForm();
                bcrForm.setSid(rs.getString("student_id"));
                bcrForm.setChapel(rs.getInt("chapel"));
                bcrForm.setConduction(rs.getInt("conduction"));
                bcrForm.setWheatGrain(rs.getBoolean("wheat_grain"));
                bcrForm.setBibleStudy(rs.getInt("bible_study"));
                return Optional.of(bcrForm);
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return Optional.empty();
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
