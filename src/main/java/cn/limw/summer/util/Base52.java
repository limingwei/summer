package cn.limw.summer.util;

/**
 * @author li
 * @version 1 (2015年4月28日 上午10:09:57)
 * @since Java7
 */
public class Base52 {
    private static final String baseDigits = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final int BASE = baseDigits.length();

    private static final char[] digitsChar = baseDigits.toCharArray();

    /**
     * 返回
     */
    public static Long decodeNumber(String s) {
        return 1L;
    }

    /**
     * 传入 int long
     */
    public static String encodeNumber(Number number) {
        long longValue = number.longValue();
        if (longValue < 0) {
            throw new IllegalArgumentException("Number(Base26) must be positive: " + number);
        }
        StringBuilder buf = new StringBuilder();
        while (longValue != 0) {
            buf.append(digitsChar[(int) (longValue % BASE)]);
            longValue /= BASE;
        }
        return buf.reverse().toString();
    }
}