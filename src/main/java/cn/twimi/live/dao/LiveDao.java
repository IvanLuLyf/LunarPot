package cn.twimi.live.dao;

import cn.twimi.live.model.Live;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface LiveDao {
    String TABLE_NAME = "tp_live";

    @Select("select * from " + TABLE_NAME + " where id=#{id} limit 1")
    Live getById(long id);

    @Select("select count(1) from " + TABLE_NAME)
    int count();

    @Select({"<script>",
            "<bind name=\"start_pos\" value=\"(page-1)*size\" />",
            "select * from ", TABLE_NAME, " order by id",
            "<if test=\"page &gt; 0 and size &gt; 0\"> limit #{size} offset #{start_pos} </if>",
            "</script>"})
    List<Live> listByPage(int page, int size);

    @Select({"<script>",
            "<bind name=\"start_pos\" value=\"(page-1)*size\" />",
            "select * from ",
            TABLE_NAME,
            "<where>",
            "<if test=\"param.keyword!=null\"> <bind name=\"search\" value=\"'%'+param.keyword+'%'\" /> title like #{search} </if>",
            "<if test=\"param.state!=null\"> and state=#{param.state} </if>",
            "<if test=\"param.user_id!=null\"> and user_id=#{param.user_id} </if>",
            "</where>",
            " order by id",
            "<if test=\"page &gt; 0 and size &gt; 0\"> limit #{size} offset #{start_pos} </if>",
            "</script>"})
    List<Live> listBy(Map<String, String> param, int page, int size);

    @Select("select * from " + TABLE_NAME + " where user_id=#{userId} and state=#{state}")
    List<Live> listByUserIdAndState(long userId, int state);

    @Select("select count(1)>0 as auth from " + TABLE_NAME + " where user_id=#{userId} and id=#{liveId} and state=#{state}")
    boolean checkLiveAvailable(long userId, long liveId, int state);

    @Update("update " + TABLE_NAME + " set state=#{state} where id=#{id}")
    int updateState(long id, int state);

    @Update("update " + TABLE_NAME + " set extra=#{extra} where id=#{id}")
    int updateExtra(long id, String extra);

    @Insert({"insert into ",
            TABLE_NAME,
            "(user_id,title,create_time,state)",
            " values ",
            "(#{userId},#{title},#{createTime},#{state})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int create(Live live);
}
