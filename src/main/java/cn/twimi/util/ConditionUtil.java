package cn.twimi.util;

import cn.twimi.common.vo.Condition;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionUtil {
    private static final Pattern PATTERN_WORD = Pattern.compile("^[_a-zA-Z0-9]+$");

    public static String parse(Condition c, Map<String, Object> v) {
        String sql = parse(c, v, 1);
        if ("".equals(sql)) return null;
        return sql;
    }

    private static String parse(Condition c, Map<String, Object> v, int l) {
        String type = c.getT();
        if ("and".equals(type) || "or".equals(type)) {
            boolean hasLeft = false;
            String left = "";
            Condition cl = c.getL(), cr = c.getR();
            if (cl != null) {
                hasLeft = true;
                left = parse(c.getL(), v, l * 2 - 1);
            }
            if (cr != null) {
                String right = parse(c.getR(), v, l * 2);
                if (hasLeft) {
                    return "(" + left + ") " + type + " (" + right + ")";
                }
                return right;
            }
            return "";
        } else if ("=".equals(type) || "!=".equals(type)
                || "<".equals(type) || ">".equals(type)
                || "<=".equals(type) || ">=".equals(type)
        ) {
            Matcher m = PATTERN_WORD.matcher(c.getK());
            if (!m.find()) return "";
            String key = c.getK() + l;
            v.put(key, c.getV());
            return StrUtil.toUnderscore(c.getK()) + type + "#{param." + key + "}";
        }
        return "";
    }
}
