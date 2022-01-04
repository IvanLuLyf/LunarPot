package cn.twimi.common.dao;

import cn.twimi.util.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface BaseDao<T> {
    String TABLE_NAME = null;

    @InsertProvider(type = SqlUtil.class, method = SqlUtil.INSERT)
    int insert(T o);

    @UpdateProvider(type = SqlUtil.class, method = SqlUtil.UPDATE)
    int update(T o);

    @SelectProvider(type = SqlUtil.class, method = SqlUtil.SELECT_BY)
    List<T> listBy(
            @Param("where") String where,
            @Param("param") Map<String, Object> value,
            @Param("page") int page,
            @Param("size") int size,
            @Param("order") String order,
            @Param("table") String table
    );

    @SelectProvider(type = SqlUtil.class, method = SqlUtil.COUNT_BY)
    int countBy(
            @Param("where") String where,
            @Param("param") Map<String, Object> value,
            @Param("table") String table
    );

    @SelectProvider(type = SqlUtil.class, method = SqlUtil.SELECT_BY)
    T selectOneBy(
            @Param("where") String where,
            @Param("param") Map<String, Object> value,
            @Param("table") String table
    );

    @SelectProvider(type = SqlUtil.class, method = SqlUtil.SELECT_BY_ID)
    T selectOneById(
            @Param("id") long id,
            @Param("table") String table
    );
}
