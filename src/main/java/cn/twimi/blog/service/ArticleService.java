package cn.twimi.blog.service;

import cn.twimi.response.ApiResponse;
import cn.twimi.blog.model.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    ApiResponse<Article> create(Article article);

    ApiResponse<Article> get(long id);

    List<Article> getArticlesByPage(int page, int limit);

    List<Article> getArticlesBySearch(String keyword, int page, int limit);
}
