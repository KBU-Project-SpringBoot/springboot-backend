package wiseSWlife.wiseSWlife.db.entity.majorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.db.repository.majorRepository.MajorRepository;
import wiseSWlife.wiseSWlife.model.graduation.form.MajorForm;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Repository
@Primary
@RequiredArgsConstructor
public class JdbcMajorRepository implements MajorRepository {

    private final DataSource dataSource;

    @Override
    public MajorForm save(MajorForm majorForm) {
        String sql = "insert into Major_TB (student_id, major_begin_and_requirement_arr, major_requirement_credit, major_select_arr, common_major, future_design) values(?, ?, ?, ?, ?, ?)";


        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        //Object[] data = majorForm.getMajorBeginAndRequirementArr().toArray(new Object[majorForm.getMajorBeginAndRequirementArr().size()]);
        //Array sqlArray = getConnection().createArrayOf("VARCHAR", new Object[]{"1", "2","3"});

        //Object[] data2 = majorForm.getMajorSelectArr().toArray(new Object[majorForm.getMajorSelectArr().size()]);
        //Array sqlArray2 = getConnection().createArrayOf("VARCHAR", new Object[]{"1", "2","3"});
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, majorForm.getSid());
            pstmt.setString(2, majorForm.getMajorBeginAndRequirementArr().toString().substring(1, majorForm.getMajorBeginAndRequirementArr().toString().length()-1));
            pstmt.setInt(3, majorForm.getMajorRequirementCredit());
            pstmt.setString(4, majorForm.getMajorSelectArr().toString().substring(1,majorForm.getMajorSelectArr().toString().length()-1));
            pstmt.setInt(5, majorForm.getCommonMajor());
            pstmt.setInt(6, majorForm.getFutureDesign());

            pstmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(conn, pstmt, rs);
        }

        return majorForm;
    }

    @Override
    public MajorForm update(MajorForm majorForm) {
        String sql = "update Major_TB set student_id = ?, major_begin_and_requirement_arr = ?, major_requirement_credit = ?, major_select_arr = ?, common_major = ?, future_design = ? where student_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, majorForm.getSid());
            pstmt.setString(2, majorForm.getMajorBeginAndRequirementArr().toString().substring(1, majorForm.getMajorBeginAndRequirementArr().toString().length()-1));
            pstmt.setInt(3, majorForm.getMajorRequirementCredit());
            pstmt.setString(4, majorForm.getMajorSelectArr().toString().substring(1, majorForm.getMajorSelectArr().toString().length()-1));
            pstmt.setInt(5, majorForm.getCommonMajor());
            pstmt.setInt(6, majorForm.getFutureDesign());
            pstmt.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return majorForm;
    }

    @Override
    public Optional<MajorForm> findMajorBySid(String sid) {
        String sql = "select * from Major_TB where student_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sid);
            rs = pstmt.executeQuery();

            if(rs.next()){
                MajorForm majorForm = new MajorForm();
                majorForm.setSid(rs.getString("student_id"));
                majorForm.setMajorBeginAndRequirementArr(new ArrayList<>(Arrays.asList(rs.getString("major_begin_and_requirement_arr").split(", "))));
                majorForm.setMajorRequirementCredit(rs.getInt("major_requirement_credit"));
                majorForm.setMajorSelectArr(new ArrayList<>(Arrays.asList(rs.getString("major_select_arr").split(", "))));
                majorForm.setCommonMajor(rs.getInt("common_major"));
                majorForm.setFutureDesign(rs.getInt("future_design"));
                return Optional.of(majorForm);
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
