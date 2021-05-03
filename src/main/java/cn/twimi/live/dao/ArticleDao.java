package cn.twimi.live.dao;

import cn.twimi.live.model.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ArticleDao {
    @Insert("insert into tp_article(user_id,title,content,create_time,state) values (#{userId},#{title},#{content},#{createTime},#{state})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int create(Article live);

    @Select({"<script>",
            "<bind name=\"st\" value=\"(_parameter.page-1)*_parameter.size\" />",
            "select * from tp_article order by id limit #{size} offset #{st}",
            "</script>"})
    List<Article> getArticleByPage(@Param("page") int page, @Param("size") int size);
}
