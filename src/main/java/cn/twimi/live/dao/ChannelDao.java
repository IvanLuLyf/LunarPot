package cn.twimi.live.dao;

import cn.twimi.live.model.Channel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ChannelDao {
    @Select("select * from tp_channel where id=#{id} limit 1")
    Channel getChannelById(long id);

    @Select("select * from tp_channel order by id limit #{size} offset #{st}")
    List<Channel> getChannelsByPage(@Param("st") int start, @Param("size") int size);

    @Select("select count(1) from tp_channel")
    int count();

    @Select("select from tp_channel where user_id=#{userId} order by id")
    List<Channel> getChannelsByUserId(long userId);

    @Select("select * from tp_channel where title like #{search}")
    List<Channel> getChannelsBySearch(String search);

    @Insert("insert into tp_channel(user_id,title,create_time,state) values (#{userId},#{title},#{createTime},#{state})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int create(Channel project);
}
