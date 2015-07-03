package cn.limw.summer.util;

import java.util.Random;

/**
 * @author lgb
 * @version 1 (2015年1月20日下午5:56:06)
 * @since Java7
 */
public class RandomStringUtil {
    /**
     * 随机字符串类型：纯数字
     */
    public static final int RANDOM_TYPE_NUMBER = 1;

    /**
     * 随机字符串类型：纯字母
     */
    public static final int RANDOM_TYPE_LETTER = 2;

    /**
     * 随机字符串类型：混合
     */
    public static final int RANDOM_TYPE_MIXED = 3;

    /**
     * 生成随机字符串。其中type指定随机字符串类型，取值范围: RANDOM_TYPE_NUMBER, RANDOM_TYPE_LETTER,
     * RANDOM_TYPE_MIXED
     * @param type 随机字符串类型
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String random(int type, int length) {
        byte[] b = new byte[length];
        switch (type) {
        case RANDOM_TYPE_NUMBER: {
            for (int i = 0; i < b.length; i++) {
                b[i] = Maths.randomByte((byte) '0', (byte) '9');
            }
            break;
        }
        case RANDOM_TYPE_LETTER: {
            Random random = new Random();
            for (int i = 0; i < b.length; i++) {
                b[i] = Maths.randomByte((byte) 'a', (byte) 'z');
                if (random.nextBoolean()) {
                    b[i] = Maths.randomByte((byte) 'A', (byte) 'Z');
                }
            }
            break;
        }
        case RANDOM_TYPE_MIXED: {
            Random random = new Random();
            for (int i = 0; i < b.length; i++) {
                b[i] = Maths.randomByte((byte) '0', (byte) '9');
                if (random.nextBoolean()) {
                    b[i] = Maths.randomByte((byte) 'a', (byte) 'z');
                }
                if (random.nextBoolean()) {
                    b[i] = Maths.randomByte((byte) 'A', (byte) 'Z');
                }
            }
            break;
        }
        }
        return new String(b);
    }

    /**
     * 判断指定字符串中是否包含指定比较字符集合中的字符
     * @param s 字符串
     * @param chars 比较字符集合
     * @return true if 指定字符串中包含指定字符集合中的字符, otherwise false
     */
    public static boolean containsChar(String s, String chars) {
        if (StringUtil.isEmpty(chars)) {
            return false;
        }
        byte[] bc = chars.getBytes();
        for (byte b : bc) {
            if (s.indexOf((char) b) >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成纯字母组合的随机字符串
     * @param length 长度
     * @param ingoredChars 要忽略的字符集合
     * @return 纯字母组合的随机字符串
     */
    public static String randomLetters(int length, String ingoredChars) {
        String s = random(RANDOM_TYPE_LETTER, length);
        while (containsChar(s, ingoredChars)) {
            s = random(RANDOM_TYPE_LETTER, length);
        }
        return s;
    }

    /**
     * 生成纯数字组合的随机字符串
     * @param length 长度
     * @param ingoredChars 要忽略的字符集合
     * @return 纯数字组合的随机字符串
     */
    public static String randomNumbers(int length, String ingoredChars) {
        String s = random(RANDOM_TYPE_NUMBER, length);
        while (containsChar(s, ingoredChars)) {
            s = random(RANDOM_TYPE_NUMBER, length);
        }
        return s;
    }

    /**
     * 生成数字和字母混合的随机字符串
     * @param length 长度
     * @param ingoredChars 要忽略的字符集合
     * @return 数字和字母混合的随机字符串
     */
    public static String randomMixeds(int length, String ingoredChars) {
        String s = random(RANDOM_TYPE_MIXED, length);
        while (containsChar(s, ingoredChars)) {
            s = random(RANDOM_TYPE_MIXED, length);
        }
        return s;
    }
}