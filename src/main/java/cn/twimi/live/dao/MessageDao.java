package cn.twimi.live.dao;

import cn.twimi.live.model.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface MessageDao {
    String TABLE_NAME = "tp_message";

    @Insert({"insert into ",
            TABLE_NAME,
            "(live_id,type,content,extra,timestamp)",
            " values ",
            "(#{liveId},#{type},#{content},#{extra},#{timestamp})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int create(Message message);

    @Select({"<script>",
            "<bind name=\"start_pos\" value=\"(page-1)*size\" />",
            "select * from ",
            TABLE_NAME,
            "<where>",
            "<if test=\"param.live_id!=null\"> and live_id=#{live_id} </if>",
            "</where>",
            " order by id",
            "<if test=\"page &gt; 0 and size &gt; 0\"> limit #{size} offset #{start_pos} </if>",
            "</script>"})
    List<Message> listBy(Map<String, String> param, int page, int size);
}
