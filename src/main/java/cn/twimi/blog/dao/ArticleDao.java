package cn.twimi.blog.dao;

import cn.twimi.blog.model.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface ArticleDao {
    String TABLE_NAME = "tp_article";

    @Select("select * from " + TABLE_NAME + " where id=#{id} limit 1")
    Article getById(long id);

    @Select("select count(1) from " + TABLE_NAME)
    int count();

    @Select({"<script>",
            "<bind name=\"start_pos\" value=\"(page-1)*size\" />",
            "select * from ", TABLE_NAME, " order by id desc",
            "<if test=\"page &gt; 0 and size &gt; 0\"> limit #{size} offset #{start_pos} </if>",
            "</script>"})
    List<Article> listByPage(int page, int size);

    @Select({"<script>",
            "<bind name=\"start_pos\" value=\"(page-1)*size\" />",
            "select * from ",
            TABLE_NAME,
            "<where>",
            "<if test=\"param.keyword!=null\"> <bind name=\"search\" value=\"'%'+param.keyword+'%'\" /> title like #{search} or content like #{search} </if>",
            "<if test=\"param.state!=null\"> and state=#{param.state} </if>",
            "<if test=\"param.user_id!=null\"> and user_id=#{param.user_id} </if>",
            "</where>",
            "<if test=\"param.orderBy!=null\"> order by #{param.orderBy} </if>",
            "<if test=\"page &gt; 0 and size &gt; 0\"> limit #{size} offset #{start_pos} </if>",
            "</script>"})
    List<Article> listBy(Map<String, Object> param, int page, int size);

    @Insert({"insert into ",
            TABLE_NAME,
            "(user_id,title,content,create_time,state)",
            " values ",
            "(#{userId},#{title},#{content},#{createTime},#{state})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int create(Article article);
}
