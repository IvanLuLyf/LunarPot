package cn.twimi.live.service;

import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.model.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    ApiResponse<Article> create(Article article);

    ApiResponse<Article> get(long id);

    List<Article> getArticlesByPage(int page, int limit);
}
