package cn.limw.summer.util;

/**
 * @author li
 * @version 1 (2014年7月30日 上午11:34:14)
 * @since Java7
 */
public class Threads {
    public static void sleep(Number millis) {
        try {
            Thread.sleep(millis.longValue());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void wait(Object target) {
        try {
            target.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void wait(Object target, Number timeout) {
        try {
            target.wait(timeout.longValue());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void wait(Object target, Number timeout, Number nanos) {
        try {
            target.wait(timeout.longValue(), nanos.intValue());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}