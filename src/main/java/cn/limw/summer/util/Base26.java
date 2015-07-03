package cn.limw.summer.util;

/**
 * @author li
 * @version 1 (2015年5月19日 上午11:19:16)
 * @since Java7
 */
public class Base26 {
    private static final String baseDigits = "abcdefghijklmnopqrstuvwxyz";

    private static final int BASE = baseDigits.length();

    private static final char[] digitsChar = baseDigits.toCharArray();

    /**
     * 返回
     */
    public static Long decodeNumber(String string) {
        return 1L;
    }

    /**
     * 传入 int long
     */
    public static String encodeNumber(Number number) {
        long longValue = number.longValue();
        if (longValue <= 0) {
            throw new IllegalArgumentException("Number(Base26) must be positive: " + number);
        }

        StringBuilder buf = new StringBuilder();
        while (longValue != 0) {
            buf.append(digitsChar[(int) (longValue % BASE)]);
            longValue = longValue / BASE;
        }
        return buf.reverse().toString();
    }
}