package repository;

import db.DBConn;
import dto.ArticleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {

    private final Connection conn;

    public ArticleRepository(Connection conn) {
        this.conn = DBConn.getConnection();
    }

    public int deleteById(int id) throws Exception {
        PreparedStatement psmt = null;
        int result = 0;
        String sql = "DELETE FROM article WHERE id = ?";
        psmt = conn.prepareStatement(sql);
        psmt.setInt(1, id);
        result = psmt.executeUpdate();
        psmt.close();

        return result;
    }

    public int insertData(ArticleDto dto) throws Exception {
        PreparedStatement psmt = null;
        int result = 0;
        String sql = "INSERT INTO article (name, title, content) VALUES (?,?,?)";
        psmt = conn.prepareStatement(sql);
        psmt.setString(1, dto.getName());
        psmt.setString(2, dto.getTitle());
        psmt.setString(3, dto.getContent());
        result = psmt.executeUpdate();
        psmt.close();
        return result;
    }

    public List<ArticleDto> findById(int id) throws SQLException {
        List<ArticleDto> dtoList = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM article WHERE id = ?";
        psmt = conn.prepareStatement(sql);
        psmt.setInt(1, id);
        rs = psmt.executeQuery();

        while (rs.next()) {
            ArticleDto dto = new ArticleDto();
            dto.setId(rs.getLong("id"));
            dto.setName(rs.getString("name"));
            dto.setTitle(rs.getString("title"));
            dto.setContent(rs.getString("content"));

            dtoList.add(dto);
        }
        psmt.close();
        rs.close();

        return dtoList;
    }

    public List<ArticleDto> findAll() throws Exception{
        List<ArticleDto> dtoList = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM article ORDER BY name";
        psmt = conn.prepareStatement(sql);
        rs =  psmt.executeQuery();

        while (rs.next()) {
            ArticleDto dto = new ArticleDto();
            dto.setId(rs.getLong("id"));
            dto.setName(rs.getString("name"));
            dto.setTitle(rs.getString("title"));
            dto.setContent(rs.getString("content"));
            dtoList.add(dto);
        }
        psmt.close();
        rs.close();

        return dtoList;
    }

    public void update(ArticleDto updateData) throws Exception{
        PreparedStatement psmt = null;

        int result = 0;
        String sql = "UPDATE article ";
        sql = sql + "SET name = ?, ";
        sql = sql + "title = ?, ";
        sql = sql + "content = ? ";
        sql = sql + "WHERE id = ?";
        psmt = conn.prepareStatement(sql);
        psmt.setString(1, updateData.getName());
        psmt.setString(2, updateData.getTitle());
        psmt.setString(3, updateData.getContent());
        psmt.setLong(4, updateData.getId());
        psmt.executeUpdate();
        psmt.close();;


    }
}
