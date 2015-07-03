package cn.limw.summer.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author li ( limingwei@mail.com )
 * @version 1 ( 2014年5月16日 下午4:38:16 )
 * @see org.springframework.util.ObjectUtils#nullSafeEquals(Object, Object)
 */
public class StringUtil {
    public static String join(String linker, String prefix, String suffix, Object[] values) {
        assert null != values;
        if (values.length == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < values.length - 1; i = i + 1) {
            stringBuffer.append(prefix).append(values[i]).append(suffix).append(linker);
        }
        stringBuffer.append(prefix).append(values[values.length - 1]).append(suffix);
        return stringBuffer.toString();
    }

    /**
     * @see #join(String, String, String, Object[])
     */
    public static String join(String linker, String prefix, String suffix, List<String> values) {
        return join(linker, prefix, suffix, values.toArray());
    }

    /**
     * @see #join(String, String, String, Object[])
     */
    public static String join(String linker, String prefix, String suffix, Set<String> values) {
        return join(linker, prefix, suffix, values.toArray());
    }

    /**
     * @see #join(String, String, String, Object[])
     */
    public static String join(String linker, Object[] values) {
        if (null != values && values.length > 0 && values[0].getClass().isArray()) {
            values = (Object[]) values[0]; // 传入参数是 Serializable[] 数组时,会被判断为非数组
        }
        return join(linker, "", "", values);
    }

    /**
     * @see #join(String, String, String, Object[])
     */
    public static String join(String linker, List values) {
        return join(linker, values.toArray());
    }

    /**
     * 在数组中搜索包含特定字符串的项
     */
    public static String searchContains(String[] values, String value, String defaultValue) {
        for (String _value : values) {
            if (containsIgnoreCase(_value, value)) {
                return _value;
            }
        }
        return defaultValue;
    }

    /**
     * 用StringBuffer拼接字符串
     */
    public static String concat(String... items) {
        assert null != items;
        StringBuffer stringBuffer = new StringBuffer();
        for (String each : items) {
            stringBuffer.append(each);
        }
        return stringBuffer.toString();
    }

    public static String[] split(String string, String regex) {
        if (null == string) {
            return new String[0];
        }
        return string.split(regex);
    }

    public static Integer[] splitToIntArray(String string, String regex) {
        String[] stringArray = split(string, regex);
        Integer[] intArray = new Integer[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Nums.toInt(stringArray[i]);
        }
        return intArray;
    }

    public static Boolean isEmpty(CharSequence cs) {
        return null == cs || cs.length() == 0;
    }

    public static String alignRight(Object o, int width, String c) {
        if (null == o)
            return null;
        String s = o.toString();
        int length = s.length();
        if (length >= width)
            return s;
        return new StringBuilder().append(dup(c, width - length)).append(s).toString();
    }

    public static String dup(String cs, int num) {
        if (null == cs || num <= 0)
            return "";
        StringBuilder sb = new StringBuilder(cs.length() * num);
        for (int i = 0; i < num; i++)
            sb.append(cs);
        return sb.toString();
    }

    public static String toString(Object object, String valueWhenNull) {
        return null == object ? valueWhenNull : object.toString();
    }

    public static String toString(Object object) {
        return toString(object, "");
    }

    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();//32位加密 
            //return buf.toString().substring(8, 24);//16位的加密     
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean startWith(String string, String prefix) {
        return null != string && null != prefix && string.startsWith(prefix);
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * @param content
     * @param beginIndex
     * @param endIndex 不是长度,为负数时表示从尾部开始截取
     */
    public static String substring(String content, Integer beginIndex, Integer endIndex) {
        if (endIndex < 0) {
            int len = content.length();
            return content.substring(len - beginIndex - (-endIndex), len - beginIndex);
        } else {
            return content.substring(beginIndex, endIndex);
        }
    }

    public static String upperFirst(CharSequence s) {
        if (null == s)
            return null;
        int len = s.length();
        if (len == 0)
            return "";
        char c = s.charAt(0);
        if (Character.isUpperCase(c))
            return s.toString();
        return new StringBuilder(len).append(Character.toUpperCase(c)).append(s.subSequence(1, len)).toString();
    }

    public static String toHexString(byte[] datas) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < datas.length; i++) {
            String hex = Integer.toHexString(datas[i] & 0xFF);
            if (hex.length() <= 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 返回第一个非空的值
     */
    public static String coalesce(String... strs) {
        for (String each : strs) {
            if (!isEmpty(each)) {
                return each;
            }
        }
        return null;
    }

    public static Boolean equals(String str1, String str2) {
        return (null == str1 && null == str2) || (null != str1 && str1.equals(str2));
    }

    public static Boolean equalsIgnoreCase(String str1, String str2) {
        return (null == str1 && null == str2) || (null != str1 && str1.equalsIgnoreCase(str2));
    }

    public static Boolean endWith(String string, String str) {
        return null != string && null != str && string.toLowerCase().endsWith(str.toLowerCase());
    }

    /**
     * new String("abc".getBytes("utf-8"), "ISO8859-1"))
     */
    public static String utf8ToIso88591(String input) {
        try {
            return new String(input.getBytes("utf-8"), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String escapeNull(String string) {
        if (null == string || string.trim().isEmpty() || "null".equalsIgnoreCase(string.trim())) {
            return null;
        } else {
            return string.trim();
        }
    }

    public static Integer length(String value) {
        return null == value ? 0 : value.length();
    }

    public static Boolean isAllEmpty(String[] array) {
        if (null == array || array.length < 1) {
            return true;
        } else {
            for (String each : array) {
                if (!isEmpty(each)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static String truncation(String s, int length) {
        if (StringUtil.isEmpty(s)) {
            return "";
        }
        s = s.replaceAll("<[^>]*>", "");
        if (s.length() > length) {
            s = s.substring(0, length) + "...";
        }
        return s;
    }

    /**
     * 复制字符串
     * @param cs 字符串
     * @param num 数量
     * @return 新字符串
     */
    public static String dup(CharSequence cs, int num) {
        if (isEmpty(cs) || num <= 0)
            return "";
        StringBuilder sb = new StringBuilder(cs.length() * num);
        for (int i = 0; i < num; i++)
            sb.append(cs);
        return sb.toString();
    }

    /**
     * 保证字符串为一固定长度。超过长度，切除右侧字符，否则右侧填补字符。
     * @param string 字符串
     * @param width 长度
     * @param c 补字符
     * @return 修饰后的字符串
     */
    public static String cutLeft(String string, int width, char c) {
        if (null == string)
            return null;
        int len = string.length();
        if (len == width)
            return string;
        if (len < width)
            return string + dup(c + "", width - len);
        return string.substring(0, width);
    }

    public static String cutLeft(String string, int width, String suffix) {
        if (null == string)
            return null;
        int len = string.length();
        if (len == width)
            return string;
        if (len < width)
            return string;
        return string.substring(0, width) + suffix;
    }

    public static String convert(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;
        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }
        return sb.toString();
    }

    public static String trim(String string) {
        return null == string ? null : string.trim();
    }

    public static String toUpperCase(String string) {
        return null == string ? null : string.toUpperCase();
    }

    public static String emptyWhenNull(String string) {
        return null == string ? "" : string;
    }

    public static String maxLength(String string, Integer length) {
        if (null == string || string.length() <= length) {
            return string;
        } else {
            return string.substring(0, length);
        }
    }

    /**
     * 判断是否包含
     */
    public static Boolean containsIgnoreCase(String string, String str) {
        return null != string && null != str && string.toUpperCase().contains(str.toUpperCase());
    }

    public static Boolean contains(String string, String str) {
        return null != string && null != str && string.contains(str);
    }

    public static String join(String linker, int[] intArray) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < intArray.length; i++) {
            list.add(new Integer(intArray[i]));
        }
        return join(linker, list);
    }

    public static String join(String linker, char[] charArray) {
        List<Character> list = new ArrayList<Character>();
        for (int i = 0; i < charArray.length; i++) {
            list.add(new Character(charArray[i]));
        }
        return join(linker, list);
    }

    public static Boolean isBlank(String str) {
        return isEmpty(str) || str.trim().isEmpty();
    }

    /**
     * 判断一个字符串是否仅由数字字母和点组成
     */
    public static Boolean isLetterDigitDot(String string) {
        if (StringUtil.isEmpty(string)) {
            return false;
        }

        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (Character.isLetter(c) || Character.isDigit(c) || '.' == c) {
                //
            } else {
                return false;
            }
        }
        return true;
    }
}