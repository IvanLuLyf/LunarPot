package cn.twimi.live.controller;

import cn.twimi.common.annotation.Permission;
import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.common.PageData;
import cn.twimi.live.model.Article;
import cn.twimi.live.model.User;
import cn.twimi.live.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/article")
@CrossOrigin
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Permission({User.GROUP})
    @PostMapping("/create")
    public ApiResponse<Article> apiCreate(
            HttpServletRequest request,
            @RequestParam String title,
            @RequestParam String content) {
        User user = (User) request.getAttribute("curUser");
        return articleService.create(new Article(user.getId(), title, content));
    }

    @RequestMapping("/get/{id}")
    public ApiResponse<Article> apiGet(
            @PathVariable long id
    ) {
        return articleService.get(id);
    }

    @RequestMapping("/list")
    public ApiResponse<PageData<Article>> apiList(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        List<Article> articles = articleService.getArticlesByPage(page, size);
        PageData<Article> articlePageData = PageData.<Article>builder()
                .list(articles).page(page)
                .total(articles.size())
                .build();
        return ApiResponse.<PageData<Article>>builder().status(0).msg("ok").data(articlePageData).build();
    }

    @RequestMapping("/search")
    public ApiResponse<PageData<Article>> apiSearch(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        List<Article> articles = articleService.getArticlesBySearch(keyword, page, size);
        PageData<Article> articlePageData = PageData.<Article>builder()
                .list(articles).page(page)
                .total(articles.size())
                .build();
        return ApiResponse.<PageData<Article>>builder().status(0).msg("ok").data(articlePageData).build();
    }
}
