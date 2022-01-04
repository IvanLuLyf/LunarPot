package cn.twimi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {
    private static final Pattern UNDERSCORE_PATTEN = Pattern.compile("_(\\w)");
    private static final Pattern CAMEL_PATTEN = Pattern.compile("[A-Z]");

    public static String toCamel(String text) {
        text = text.toLowerCase();
        Matcher matcher = UNDERSCORE_PATTEN.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String toUnderscore(String text) {
        StringBuffer sb = new StringBuffer();
        Matcher matcher = CAMEL_PATTEN.matcher(text);
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
