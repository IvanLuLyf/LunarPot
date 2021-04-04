package cn.twimi.live.util;

import java.util.HashMap;

public class IdUtil {
    private static String table = "fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF";
    private static int[] s = {11, 10, 3, 8, 4, 6};
    private static int xor = 177451812;
    private static long add = 8728348608L;
    private static HashMap<String, Integer> number2Word;

    static {
        number2Word = new HashMap<>();
        for (int i = 0, size = table.length(); i < size; i++) {
            number2Word.put(getIndex(table, i), i);
        }
    }

    private static String topOffIndex(String before, String updated, int index) {
        return before.substring(0, index) + updated + before.substring(index + 1);
    }

    private static String getIndex(String string, int index) {
        return string.substring(index, index + 1);
    }

    public static String encode(long x) {
        x = (x ^ xor) + add;
        String r = "LV1  4 1 7  ";
        for (int i = 0; i < 6; i++) {
            r = topOffIndex(r, getIndex(table, (int) ((x / Math.pow(58, i)) % 58)), s[i]);
        }
        return r;
    }

    public static long decode(String x) {
        long r = 0;
        for (int i = 0; i < 6; i++) {
            r += (long) number2Word.get(getIndex(x, s[i])) * (long) Math.pow(58, i);
        }
        return (r - add) ^ xor;
    }
}
