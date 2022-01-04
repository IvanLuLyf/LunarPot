package cn.twimi.util;

import java.util.concurrent.atomic.AtomicInteger;

public class PrimaryKeyUtil {
    private static final AtomicInteger count = new AtomicInteger(0);
    private static final int MAX_SIZE = 50000;
    private static final int BASE_NUM = 100000;

    public static long getPK(int size) {
        int c = count.getAndAdd(size);
        if (c > MAX_SIZE) count.set(0);
        return System.currentTimeMillis() / 1000 * BASE_NUM + c;
    }

    public static long getPK() {
        return getPK(1);
    }
}
