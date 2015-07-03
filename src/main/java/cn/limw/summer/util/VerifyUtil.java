package cn.limw.summer.util;

import java.util.regex.Pattern;

/**
 * @author li
 * @version 1 (2014年8月18日 下午5:20:53)
 * @since Java7
 */
public class VerifyUtil {
    /**
     * 验证字符串是否符合正则表达式
     */
    public static Boolean regex(String input, String regex) {
        return Pattern.compile(regex).matcher(input).find();
    }
}