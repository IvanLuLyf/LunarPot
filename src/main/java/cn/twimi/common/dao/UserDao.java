package cn.twimi.common.dao;

import cn.twimi.common.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface UserDao {
    String TABLE_NAME = "tp_user";

    @Select("select * from " + TABLE_NAME + " where id = #{id}")
    User getById(long id);

    @Select("select * from " + TABLE_NAME + " where username=#{username} or email=#{username} limit 1")
    User getByUsername(String username);

    @Select("select * from " + TABLE_NAME + " where token=#{token} limit 1")
    User getByToken(String token);

    @Select("select count(1) from " + TABLE_NAME)
    int count();

    @Select({"<script>",
            "<bind name=\"start_pos\" value=\"(page-1)*size\" />",
            "select * from ", TABLE_NAME, " order by id",
            "<if test=\"page &gt; 0 and size &gt; 0\"> limit #{size} offset #{start_pos} </if>",
            "</script>"})
    List<User> listByPage(int page, int size);

    @Select({"<script>",
            "<bind name=\"start_pos\" value=\"(page-1)*size\" />",
            "select id,name,username,avatar,role_id from ",
            TABLE_NAME,
            "<where>",
            "<if test=\"param.keyword!=null\"> <bind name=\"search\" value=\"'%'+param.keyword+'%'\" /> name like #{search} or username like #{search} </if>",
            "<if test=\"param.state!=null\"> and state=#{param.state} </if>",
            "</where>",
            "<if test=\"param.orderBy!=null\"> order by #{param.orderBy} </if>",
            "<if test=\"page &gt; 0 and size &gt; 0\"> limit #{size} offset #{start_pos} </if>",
            "</script>"})
    List<User> listBy(Map<String, Object> param, int page, int size);

    @Insert({"insert into ",
            TABLE_NAME,
            "(username,password,name,email,phone,create_time,role_id)",
            " values ",
            "(#{username},#{password},#{name},#{email},#{phone},#{createTime},#{roleId})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int create(User user);

    @Update("update " + TABLE_NAME + " set token=#{token},update_time=#{updateTime},expire=#{expire} where id=#{id}")
    int updateToken(User user);

    @Update("update " + TABLE_NAME + " set state=#{state} where id=#{id}")
    int updateState(long id, int state);

    @Update("update " + TABLE_NAME + " set name=#{name} where id=#{id}")
    int updateName(long id, String name);

    @Update("update " + TABLE_NAME + " set avatar=#{avatar} where id=#{id}")
    int updateAvatar(long id, String avatar);
}
