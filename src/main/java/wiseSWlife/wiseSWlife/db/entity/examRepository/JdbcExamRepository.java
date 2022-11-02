package wiseSWlife.wiseSWlife.db.entity.examRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.db.repository.examRepository.ExamRepository;
import wiseSWlife.wiseSWlife.model.graduation.form.ExamForm;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Repository
@Primary
@RequiredArgsConstructor
public class JdbcExamRepository implements ExamRepository {

    private final DataSource dataSource;

    @Override
    public ExamForm save(ExamForm examForm) {
        String sql = "insert into Exam_TB (student_id, bible, english, computer, computer2) values(?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, examForm.getSid());
            pstmt.setBoolean(2, examForm.getBible());
            pstmt.setBoolean(3, examForm.getEnglish());
            pstmt.setBoolean(4, examForm.getComputer());
            pstmt.setBoolean(5, examForm.getComputer2());
            pstmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(conn, pstmt, rs);
        }

        return examForm;
    }

    @Override
    public ExamForm update(ExamForm examForm) {
        String sql = "update Exam_TB set student_id = ?, bible = ?, english = ?, computer = ?, computer2 = ? where student_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, examForm.getSid());
            pstmt.setBoolean(2, examForm.getBible());
            pstmt.setBoolean(3, examForm.getEnglish());
            pstmt.setBoolean(4, examForm.getComputer());
            pstmt.setBoolean(5, examForm.getComputer2());
            pstmt.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }

        return examForm;
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
