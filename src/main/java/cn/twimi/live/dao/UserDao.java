package cn.twimi.live.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import cn.twimi.live.model.User;

import java.util.List;

@Mapper
@Component
public interface UserDao {
    @Select("select * from tp_user where id = #{id}")
    User getUserById(long id);

    @Select("select * from tp_user where username=#{username} or email=#{username} limit 1")
    User getUserByUsername(String username);

    @Select("select * from tp_user where token=#{token} limit 1")
    User getUserByToken(String token);

    @Select("select * from tp_user where name like #{search} or username like #{search}")
    List<User> getUsersBySearch(String search);

    @Select("select * from tp_user order by id limit #{size} offset #{st}")
    List<User> getUsersByPage(@Param("st") int start, @Param("size") int size);

    @Select("select count(1) from tp_user")
    int countUser();

    @Insert("insert into tp_user(username,password,name,email,phone,create_time,role_id) values (#{username},#{password},#{name},#{email},#{phone},#{createTime},#{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int create(User user);

    @Update("update tp_user set token=#{token},update_time=#{updateTime},expire=#{expire} where id=#{id}")
    int updateToken(User user);

    @Update("update tp_user set state=#{state} where id=#{id}")
    int updateState(long id, int state);

    @Update("update tp_user set name=#{name} where id=#{id}")
    int updateName(long id, String name);
}
