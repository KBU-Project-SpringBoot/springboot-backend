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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<ExamForm> findAll() {
        String sql = "select * from Exam_TB";
        List<ExamForm> examFormList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if(rs.next()){
                ExamForm examForm = new ExamForm(
                        rs.getString("student_id"),
                        rs.getBoolean("bible"),
                        rs.getBoolean("english"),
                        rs.getBoolean("computer"),
                        rs.getBoolean("computer2")
                );
                examFormList.add(examForm);
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            close(conn, pstmt, rs);
        }
        return examFormList;
    }

    @Override
    public Optional<ExamForm> findExamBySid(String sid) {
        String sql = "select * from Exam_TB where student_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sid);
            rs = pstmt.executeQuery();

            if(rs.next()){
                ExamForm examForm = new ExamForm();
                examForm.setSid(rs.getString("student_id"));
                examForm.setBible(rs.getBoolean("bible"));
                examForm.setEnglish(rs.getBoolean("english"));
                examForm.setComputer(rs.getBoolean("computer"));
                examForm.setComputer2(rs.getBoolean("computer2"));
                return Optional.of(examForm);
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
