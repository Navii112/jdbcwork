package dto;

public class CommentDto {
    private Long id;
    private Long articleId;
    private String author;
    private String content;

    public CommentDto() {}

    public CommentDto(Long id, Long articleId, String author, String content) {
        this.id = id;
        this.articleId = articleId;
        this.author = author;
        this.content = content;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() { return content;
    }
    public void setContent(String content) { this.content = content;
    }

    @Override
    public String toString() {
        return " └ [댓글] " + author + " : " + content;
    }
}