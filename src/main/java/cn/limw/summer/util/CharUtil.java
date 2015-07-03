package cn.limw.summer.util;

/**
 * @author li
 * @version 1 (2015年5月14日 上午10:16:48)
 * @since Java7
 */
public class CharUtil {
    public static String toStringForShow(char[] charArray) {
        if (null == charArray) {
            return "charArray is null";
        }

        String result = "";
        result += "str=" + new String(charArray);
        result += " len=" + charArray.length;
        result += " chars=" + StringUtil.join(",", charArray);
        result += " ints=" + StringUtil.join(",", toIntArray(charArray));
        return result;
    }

    private static int[] toIntArray(char[] charArray) {
        int[] array = new int[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            array[i] = charArray[i];
        }
        return array;
    }

    public static String toStringForShow(String value) {
        return null == value ? null : toStringForShow(value.toCharArray());
    }
}