package wiseSWlife.wiseSWlife.db.repository.refinementRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.dto.graduation.form.RefinementForm;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Repository
@Primary
@RequiredArgsConstructor
public class JdbcRefinementRepositoryImpl implements RefinementRepository {

    private final DataSource dataSource;

    @Override
    public RefinementForm save(RefinementForm refinementForm) {
        String sql = "insert into Refinement_TB (student_id, refinement_arr, refinement_credit, english_arr, english_credit, basic_class_credit, college_life_and_self_development) values(?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, refinementForm.getSid());
            pstmt.setString(2, refinementForm.getRefinementArr().toString().substring(1, refinementForm.getRefinementArr().toString().length()-1));
            pstmt.setInt(3, refinementForm.getRefinementCredit());
            pstmt.setString(4, refinementForm.getEnglishArr().toString().substring(1,refinementForm.getEnglishArr().toString().length()-1));
            pstmt.setInt(5, refinementForm.getEnglishCredit());
            pstmt.setInt(6, refinementForm.getBasicClassCredit());
            pstmt.setBoolean(7, refinementForm.isCollegeLifeAndSelfDevelopment());

            pstmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(conn, pstmt, rs);
        }

        return refinementForm;
    }

    @Override
    public RefinementForm update(RefinementForm refinementForm) {
        String sql = "update Refinement_TB set student_id = ?, refinement_arr = ?, refinement_credit = ?, english_arr = ?, english_credit = ?, basic_class_credit = ?, college_life_and_self_development = ? where student_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, refinementForm.getSid());
            pstmt.setString(2, refinementForm.getRefinementArr().toString().substring(1, refinementForm.getRefinementArr().toString().length()-1));
            pstmt.setInt(3, refinementForm.getRefinementCredit());
            pstmt.setString(4, refinementForm.getEnglishArr().toString().substring(1,refinementForm.getEnglishArr().toString().length()-1));
            pstmt.setInt(5, refinementForm.getEnglishCredit());
            pstmt.setInt(6, refinementForm.getBasicClassCredit());
            pstmt.setBoolean(7, refinementForm.isCollegeLifeAndSelfDevelopment());
            pstmt.setString(8, refinementForm.getSid());

            pstmt.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return refinementForm;
    }

    @Override
    public Optional<RefinementForm> findRefinementBySid(String sid) {
        String sql = "select * from Refinement_TB where student_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sid);
            rs = pstmt.executeQuery();

            if(rs.next()){
                RefinementForm refinementForm = new RefinementForm();
                refinementForm.setSid(rs.getString("student_id"));
                refinementForm.setRefinementArr(new ArrayList<>(Arrays.asList(rs.getString("refinement_arr").split(", "))));
                refinementForm.setRefinementCredit(rs.getInt("refinement_credit"));
                refinementForm.setEnglishArr(new ArrayList<>(Arrays.asList(rs.getString("english_arr").split(", "))));
                refinementForm.setEnglishCredit(rs.getInt("english_credit"));
                refinementForm.setBasicClassCredit(rs.getInt("basic_class_credit"));
                refinementForm.setCollegeLifeAndSelfDevelopment(rs.getBoolean("college_life_and_self_development"));
                return Optional.of(refinementForm);
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
