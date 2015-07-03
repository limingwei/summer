package cn.limw.summer.util;

import java.util.Stack;

/**
 * @author li
 * @version 1 (2015年4月28日 上午10:09:57)
 * @since Java7
 */
public class Base62 {
    private static char[] BASE_62_CHAR_SET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public static String toBase62(Number number) {
        Long rest = Math.abs(number.longValue());

        Stack<Character> stack = new Stack<Character>();
        while (rest != 0) {
            int index = new Long((rest - (rest / 62) * 62)).intValue();
            stack.add(BASE_62_CHAR_SET[index]);
            rest = rest / 62;
        }

        StringBuilder result = new StringBuilder();
        for (; !stack.isEmpty();) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    /**
     * 将62进制转换成10进制数
     * @param ident62
     */
    public static Integer fromBase62(String ident62) {
        int decimal = 0;
        int base = 62;
        int keisu = 0;
        int cnt = 0;

        byte ident[] = ident62.getBytes();
        for (int i = ident.length - 1; i >= 0; i--) {
            int num = 0;
            if (ident[i] > 48 && ident[i] <= 57) {
                num = ident[i] - 48;
            }
            else if (ident[i] >= 65 && ident[i] <= 90) {
                num = ident[i] - 65 + 10;
            }
            else if (ident[i] >= 97 && ident[i] <= 122) {
                num = ident[i] - 97 + 10 + 26;
            }
            keisu = (int) java.lang.Math.pow((double) base, (double) cnt);
            decimal += num * keisu;
            cnt++;
        }
        return decimal;
    }
}