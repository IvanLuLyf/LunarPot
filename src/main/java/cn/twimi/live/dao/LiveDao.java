package cn.twimi.live.dao;

import cn.twimi.live.model.Live;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface LiveDao {
    @Select("select * from tp_live where id=#{id} limit 1")
    Live getLiveById(long id);

    @Select("select * from tp_live order by id limit #{size} offset #{st}")
    List<Live> getLivesByPage(@Param("st") int start, @Param("size") int size);

    @Select("select count(1) from tp_live")
    int count();

    @Select("select * from tp_live where user_id=#{userId} order by id")
    List<Live> getLivesByUserId(long userId);

    @Select("select * from tp_live where user_id=#{userId} and state=#{state}")
    List<Live> getLivesByUserIdAndState(long userId, int state);

    @Select("select * from tp_live where title like #{search}")
    List<Live> getLivesBySearch(String search);

    @Select("select * from tp_live where title like #{search} and state=#{state}")
    List<Live> getLivesBySearchAndState(String search, int state);

    @Insert("insert into tp_live(user_id,title,create_time,state) values (#{userId},#{title},#{createTime},#{state})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int create(Live live);
}
