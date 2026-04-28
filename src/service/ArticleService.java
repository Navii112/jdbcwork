package service;

import dto.ArticleDto;
import repository.ArticleRepository;

import java.sql.SQLException;
import java.util.List;

public class ArticleService {
    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }
    public int delete(int id) throws Exception {
        return repository.deleteById(id);
    }

    public List<ArticleDto> getArticleOne(int id) throws SQLException {
        return repository.findById(id);
    }

    public void insert(String name, String title, String content) throws Exception{
        ArticleDto dto = new ArticleDto(0L, name, title, content);
        dto.setName(name);
        dto.setTitle(title);
        dto.setContent(content);
        int result = repository.insertData(dto);
        if (result > 0) {
            System.out.println("정상적으로 저장되었습니다.");
        }
    }

    public List<ArticleDto> getArticleAll() throws Exception{
        return repository.findAll();
    }

    public void update(ArticleDto updateData) throws Exception{
        repository.update(updateData);
    }
}
