package cn.twimi.live.dao;

import cn.twimi.live.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MessageDao {
    @Insert("insert into tp_message(live_id,type,content,extra,timestamp) values (#{liveId},#{type},#{content},#{extra},#{timestamp})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int create(Message message);
}
