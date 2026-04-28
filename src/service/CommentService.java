package service;

import dto.CommentDto;
import repository.CommentRepository;
import java.util.List;

public class CommentService {

    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    // 🌟 [추가됨] 댓글 리스트를 가져오는 비즈니스 로직
    public List<CommentDto> getComments(Long articleId) {
        return repository.getCommentsByArticleId(articleId);
    }

    public void insertComment(CommentDto comment) {
        try {
            int result = repository.insertComment(comment);

            if (result > 0) {
                System.out.println("댓글이 성공적으로 등록되었습니다.");
            } else {
                System.out.println("댓글 등록에 실패했습니다.");
            }
        } catch (Exception e) {
            System.out.println("댓글 등록 중 오류 발생 : " + e.getMessage());
        }
    }

    public void updateComment(CommentDto comment) {
        try {
            repository.updateComment(comment);
            System.out.println("댓글이 수정되었습니다.");
        } catch (Exception e) {
            System.out.println("댓글 수정 중 오류 발생 : " + e.getMessage());
        }
    }

    public void deleteComment(Long deleteCommentId) {
        try {
            repository.deleteComment(deleteCommentId);
            System.out.println("댓글이 삭제되었습니다.");
        } catch (Exception e) {
            System.out.println("댓글 삭제 중 오류 발생 : " + e.getMessage());
        }
    }
}