package cn.limw.summer.util;

/**
 * @author li
 * @version 1 (2015年5月19日 上午11:19:10)
 * @since Java7
 */
public class Base36 {
    private static final String baseDigits = "0123456789abcdefghijklmnopqrstuvwxyz";

    private static final int BASE = baseDigits.length();

    private static final char[] digitsChar = baseDigits.toCharArray();

    private static final int FAST_SIZE = 'z';

    private static final int[] digitsIndex = new int[FAST_SIZE + 1];

    static {
        for (int i = 0; i < FAST_SIZE; i++) {
            digitsIndex[i] = -1;
        }
        for (int i = 0; i < BASE; i++) {
            digitsIndex[digitsChar[i]] = i;
        }
    }

    /**
     * 返回
     */
    public static Long decodeNumber(String s) {
        long result = 0L;
        long multiplier = 1;
        for (int pos = s.length() - 1; pos >= 0; pos--) {
            int index = getIndex(s, pos);
            result += index * multiplier;
            multiplier *= BASE;
        }
        return result;
    }

    /**
     * 传入 int long
     */
    public static String encodeNumber(Number number) {
        long longValue = number.longValue();
        if (longValue < 0) {
            throw new IllegalArgumentException("Number(Base36) must be positive: " + number);
        }
        if (longValue == 0) {
            return "0";
        }
        StringBuilder buf = new StringBuilder();
        while (longValue != 0) {
            buf.append(digitsChar[(int) (longValue % BASE)]);
            longValue /= BASE;
        }
        return buf.reverse().toString();
    }

    private static int getIndex(String s, int pos) {
        char c = s.charAt(pos);
        if (c > FAST_SIZE) {
            throw new IllegalArgumentException("Unknow character for Base36: " + s);
        }
        int index = digitsIndex[c];
        if (index == -1) {
            throw new IllegalArgumentException("Unknow character for Base36: " + s);
        }
        return index;
    }
}