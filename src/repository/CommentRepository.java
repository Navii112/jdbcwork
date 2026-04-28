package repository;

import db.DBConn;
import dto.ArticleDto;
import dto.CommentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository {

    private final Connection conn;

    public CommentRepository(Connection conn) {
        this.conn = DBConn.getConnection();
    }

    // ================= 게시글 ================= //

    public List<ArticleDto> all() {
        List<ArticleDto> list = new ArrayList<>();
        String sql = "SELECT * FROM articles";

        try (PreparedStatement psmt = conn.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {

            while (rs.next()) {
                ArticleDto article = new ArticleDto();
                // TODO: article 세팅 로직
                list.add(article);
            }
        } catch (Exception e) {
            System.out.println("게시글 전체 조회 오류 : " + e.getMessage());
        }
        return list;
    }

    public void newArticle(ArticleDto article) {}
    public ArticleDto detail(Long id) { return null; }
    public boolean delete(Long id) { return false; }
    public void update(ArticleDto article) {}


    // ================= 댓글 ================= //

    // 1. 특정 게시글의 댓글만 가져오는 메서드
    public List<CommentDto> getCommentsByArticleId(Long articleId) {
        List<CommentDto> commentList = new ArrayList<>();
        // 🌟 수정: WHERE articleId -> WHERE article_id
        String sql = "SELECT * FROM comments WHERE article_id = ?";

        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setLong(1, articleId);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    CommentDto dto = new CommentDto();
                    // 🌟 수정: DB 컬럼명에 맞게 매핑
                    dto.setId(rs.getLong("comment_id"));
                    dto.setArticleId(rs.getLong("article_id"));
                    dto.setAuthor(rs.getString("name"));
                    dto.setContent(rs.getString("content"));

                    commentList.add(dto);
                }
            }
        } catch (Exception e) {
            System.out.println("댓글 목록 조회 중 오류 발생 : " + e.getMessage());
        }
        return commentList;
    }

    // 2. 댓글 등록
    public int insertComment(CommentDto comment) {
        int result = 0;
        // 🌟 수정: articleId, author -> article_id, name
        String sql = "INSERT INTO comments (article_id, name, content) VALUES (?, ?, ?)";

        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setLong(1, comment.getArticleId());
            psmt.setString(2, comment.getAuthor());
            psmt.setString(3, comment.getContent());

            result = psmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("댓글 INSERT 오류 : " + e.getMessage());
        }
        return result;
    }

    // 3. 댓글 수정
    public void updateComment(CommentDto comment) {
        // 🌟 수정: WHERE id -> WHERE comment_id
        String sql = "UPDATE comments SET content = ? WHERE comment_id = ?";

        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, comment.getContent());
            psmt.setLong(2, comment.getId());

            psmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("댓글 UPDATE 오류 : " + e.getMessage());
        }
    }

    // 4. 댓글 삭제
    public void deleteComment(Long deleteCommentId) {
        // 🌟 수정: WHERE id -> WHERE comment_id
        String sql = "DELETE FROM comments WHERE comment_id = ?";

        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setLong(1, deleteCommentId);

            psmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("댓글 DELETE 오류 : " + e.getMessage());
        }
    }
}