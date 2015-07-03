package cn.limw.summer.util;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author li
 * @version 1 (2014年10月17日 下午4:12:39)
 * @since Java7
 */
public class Maths {
    private static final Random RANDOM = new Random();

    public static String random(Integer len) {
        String str = Math.random() + "";
        return str.substring(str.length() - len);
    }

    public static Long power(Integer base, Integer exponent) {
        Asserts.noNull(base);
        Asserts.noNull(exponent);
        Asserts.isTrue(base > 0);
        Asserts.isTrue(exponent >= 0);

        Long result = 1L;
        for (int i = 1; i <= exponent; i++) {
            result = result * base;
            if (result < 0) {
                throw new RuntimeException("数值太大 超过 long(" + Long.MAX_VALUE + ") 的范围 (" + result + ")");
            }
        }
        return result;
    }

    /**
     * 获取随机双精度浮点数
     * @param min 最小值
     * @param max 最大值
     * @return 随机双精度浮点数
     */
    public static double randomDouble(double min, double max) {
        double d = RANDOM.nextDouble();
        return min + (max - min) * d;
    }

    /**
     * 获取随机字节数
     * @param min 最小值
     * @param max 最大值
     * @return 随机字节数
     */
    public static byte randomByte(byte min, byte max) {
        return (byte) randomDouble(min, max);
    }

    /**
     * 获取随机整数
     * @param min 最小值
     * @param max 最大值
     * @return 随机整数
     */
    public static int randomInt(int min, int max) {
        return (int) randomDouble(min, max);
    }

    /**
     * 获取随机长整数
     * @param min 最小值
     * @param max 最大值
     * @return 随机长整数
     */
    public static long randomLong(long min, long max) {
        return (long) randomDouble(min, max);
    }

    /**
     * 以指定中奖几率抽奖，返回是否中奖
     * @param probability 中奖几率
     * @return true if 中奖, otherwise false
     */
    public static boolean drawLottery(double probability) {
        return randomDouble(0, 1) < probability;
    }

    /**
     * 解析转换指定字符串为长整型数字，如果字符串不是长整型数字格式，则返回0
     * @param s 字符串
     * @return 转换后的长整型数字
     */
    public static long parseLong(String s) {
        if (StringUtil.isEmpty(s)) {
            return 0;
        }
        try {
            return new BigDecimal(s).longValue();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 解析转换指定字符串为整型数字，如果字符串不是整型数字格式，则返回0
     * @param s 字符串
     * @return 转换后的整型数字
     */
    public static int parseInt(String s) {
        if (StringUtil.isEmpty(s)) {
            return 0;
        }
        try {
            return new BigDecimal(s).intValue();
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}