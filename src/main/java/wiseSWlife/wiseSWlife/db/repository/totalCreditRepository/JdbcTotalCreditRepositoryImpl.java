package wiseSWlife.wiseSWlife.db.repository.totalCreditRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.dto.graduation.form.CreditForm;

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
public class JdbcTotalCreditRepositoryImpl implements TotalCreditRepository {

    private final DataSource dataSource;

    @Override
    public CreditForm save(CreditForm creditForm) {
        String sql = "insert into Total_Credit_TB (student_id, credit) values(?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, creditForm.getSid());
            pstmt.setInt(2, creditForm.getCredit());

            pstmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(conn, pstmt, rs);
        }

        return creditForm;
    }

    @Override
    public CreditForm update(CreditForm creditForm) {
        String sql = "update Total_Credit_TB set student_id = ?, total_credit = ? where student_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, creditForm.getSid());
            pstmt.setInt(2, creditForm.getCredit());
            pstmt.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return creditForm;
    }

    @Override
    public Optional<CreditForm> findTotalCreditBySid(String sid) {
        String sql = "select * from Total_Credit_TB where student_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sid);
            rs = pstmt.executeQuery();

            if(rs.next()){
                CreditForm creditForm = new CreditForm();
                creditForm.setSid(rs.getString("student_id"));
                creditForm.setCredit(rs.getInt("credit"));
                return Optional.of(creditForm);
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
