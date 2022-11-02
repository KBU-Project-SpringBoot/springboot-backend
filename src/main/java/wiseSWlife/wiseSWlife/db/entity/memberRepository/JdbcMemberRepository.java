package wiseSWlife.wiseSWlife.db.entity.memberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.db.repository.memberRepository.MemberRepository;
import wiseSWlife.wiseSWlife.model.member.Member;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class JdbcMemberRepository implements MemberRepository  {

    private final DataSource dataSource;

    @Override
    public Member save(Member member){

        String sql = "insert into Student_TB values(?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, member.getSid());
            pstmt.setString(2, member.getName());
            pstmt.setString(3, member.getMajor());

            pstmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(conn, pstmt, rs);
        }
        return member;
    }

    @Override
    public Member update(Member member) {

        String sql = "update Student_TB set student_name = ?, student_major = ? where student_id =?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);


            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getMajor());
            pstmt.setString(3, member.getSid());
            pstmt.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }

        return member;
    }

    @Override
    public Optional<Member> findBySid(String sid) {
        String sql = "select * from Student_TB where student_id =?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sid);
            rs = pstmt.executeQuery();

            if(rs.next()){
                Member member = new Member();
                member.setSid(rs.getString("student_id"));
                member.setName(rs.getString("student_name"));
                member.setMajor(rs.getString("student_major"));
                return Optional.of(member);
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from Student_TB";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();

            while (rs.next()){
                Member member = new Member();
                member.setSid(rs.getString("student_id"));
                member.setName(rs.getString("student_name"));
                member.setMajor(rs.getString("student_major"));
                members.add(member);
            }
            return members;

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
