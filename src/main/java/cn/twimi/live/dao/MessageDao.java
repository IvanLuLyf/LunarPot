package cn.twimi.live.dao;

import cn.twimi.live.model.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MessageDao {
    @Insert("insert into tp_message(live_id,type,content,extra,timestamp) values (#{liveId},#{type},#{content},#{extra},#{timestamp})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int create(Message message);

    @Select("select * from tp_message where live_id=#{liveId} order by id limit #{size} offset #{st}")
    List<Message> getMessagesByLiveIdWithPage(long liveId, @Param("st") int start, @Param("size") int size);
}
