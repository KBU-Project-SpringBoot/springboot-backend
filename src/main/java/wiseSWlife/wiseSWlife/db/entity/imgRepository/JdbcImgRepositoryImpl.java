package wiseSWlife.wiseSWlife.db.entity.imgRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.db.repository.imgRepository.ImgRepository;
import wiseSWlife.wiseSWlife.model.img.ImgItem;
import javax.sql.DataSource;
import java.sql.*;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class JdbcImgRepositoryImpl implements ImgRepository {

    private final DataSource dataSource;

    @Override
    public ImgItem save(ImgItem imgItem) {

        String sql = "insert into Image_TB(upload_image_name, store_image_name) values(?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, imgItem.getUploadImgName());
            pstmt.setString(2, imgItem.getStoreImgName());

            rs = pstmt.executeQuery();

            if(rs.next()){
                imgItem.setManageSeq(rs.getLong(1));
            }

        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
        }

        return imgItem;
    }

    @Override
    public ImgItem findImgBySeq(Long manageSeq) {
        String sql = "select * from Image_TB where image_sequence = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,manageSeq);
            rs = pstmt.executeQuery();
            if(rs.next()){
                ImgItem imgItem = new ImgItem(rs.getString("upload_image_name"), rs.getString("store_image_name"));
                imgItem.setManageSeq(rs.getLong("image_sequence"));
                return imgItem;
            }


        }catch (Exception e){
            log.warn("Exception = {}",e);

        }finally {
            close(conn,pstmt,rs);
        }
        return null;
    }

    @Override
    public ImgItem findImgByUUID(String UUID) {
        String sql = "select * from Image_TB where store_image_name = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UUID);
            rs = pstmt.executeQuery();
            if(rs.next()){
                ImgItem imgItem = new ImgItem(rs.getString("upload_image_name"), rs.getString("store_image_name"));
                imgItem.setManageSeq(rs.getLong("image_sequence"));
                return imgItem;
            }

        }catch (Exception e){
            log.warn("Exception = {}",e);
        }finally {
            close(conn,pstmt,rs);
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
