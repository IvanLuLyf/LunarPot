package cn.twimi.live.service.impl;

import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.dao.ArticleDao;
import cn.twimi.live.model.Article;
import cn.twimi.live.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    ArticleDao articleDao;

    public ArticleServiceImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    public ApiResponse<Article> create(Article article) {
        int row = articleDao.create(article);
        if (row <= 0) {
            return ApiResponse.<Article>builder().status(-6).msg("数据库出错").build();
        }
        return ApiResponse.<Article>builder().status(0).msg("ok").data(article).build();
    }

    @Override
    public ApiResponse<Article> get(long id) {
        Article article = articleDao.getById(id);
        return ApiResponse.<Article>builder().status(0).msg("ok").data(article).build();
    }

    @Override
    public List<Article> getArticlesByPage(int page, int limit) {
        return articleDao.listByPage(page, limit);
    }
}
