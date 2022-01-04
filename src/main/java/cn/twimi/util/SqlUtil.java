package cn.twimi.util;

import cn.twimi.common.annotation.Table;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SqlUtil {
    public static final String PREFIX = "tp_";
    public static final String SELECT_BY = "selectBy";
    public static final String COUNT_BY = "countBy";
    public static final String SELECT_BY_ID = "selectById";
    public static final String INSERT = "insert";
    public static final String DELETE = "delete";
    public static final String UPDATE = "update";


    public static String selectBy(Map<String, Object> params, ProviderContext context) {
        String table = getTable(params, context);
        String where = (String) params.get("where");
        int page = (int) params.get("page");
        int size = (int) params.get("size");
        return new SQL() {{
            SELECT("*");
            FROM(table);
            if (where != null) WHERE(where);
            if (size > 0) {
                LIMIT(size);
                OFFSET((long) (Math.max(page, 1) - 1) * size);
            }
        }}.toString();
    }

    public static String countBy(Map<String, Object> params, ProviderContext context) {
        String table = getTable(params, context);
        String where = (String) params.get("where");
        return new SQL() {{
            SELECT("count(*)");
            FROM(table);
            if (where != null) WHERE(where);
        }}.toString();
    }

    public static String selectById(Map<String, Object> params, ProviderContext context) {
        String table = getTable(params, context);
        return new SQL() {{
            SELECT("*");
            FROM(table);
            WHERE("id=#{id}");
        }}.toString();
    }

    public static String insert(Object o) {
        String table = getTableName(o);
        Map<String, String> fieldMap = getFieldMap(o);
        return new SQL() {{
            INSERT_INTO(table);
            for (String k : fieldMap.keySet()) {
                VALUES(k, fieldMap.get(k));
            }
        }}.toString();
    }

    public static String update(Object o) {
        String table = getTableName(o);
        Map<String, String> fieldMap = getFieldMap(o);
        return new SQL() {{
            UPDATE(table);
            for (String key : fieldMap.keySet()) {
                if ("id".equals(key)) {
                    WHERE("id=" + fieldMap.get("id"));
                    continue;
                }
                SET(key + "=" + fieldMap.get(key));
            }
        }}.toString();
    }

    private static String getTable(Map<String, Object> params, ProviderContext context) {
        String tmp = (String) params.get("table");
        if (tmp != null) return tmp;
        String tmp2 = getTableNameByClass(context.getMapperType()).replace("_dao", "");
        if (!(PREFIX + "base").equals(tmp2)) return tmp2;
        return "";
    }

    public static String getTableName(Object o) {
        Class<?> c = o.getClass();
        return getTableNameByClass(c);
    }

    public static String getTableNameByClass(Class<?> c) {
        Table table = c.getAnnotation(Table.class);
        if (table != null) {
            return table.value();
        }
        return PREFIX + StrUtil.toUnderscore(c.getSimpleName()).substring(1);
    }

    public static Map<String, String> getFieldMap(Object o) {
        Map<String, String> map = new HashMap<>();
        Class<?> c = o.getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            String key = field.getName();
            field.setAccessible(true);
            try {
                if (field.get(o) != null) {
                    map.put(StrUtil.toUnderscore(key), "#{" + key + "}");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
