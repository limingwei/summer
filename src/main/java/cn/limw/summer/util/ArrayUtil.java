package cn.limw.summer.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.limw.summer.java.collection.NiceToStringList;

/**
 * @author li
 * @version 1 (2014年7月15日 下午4:04:20)
 * @since Java7
 */
public class ArrayUtil {
    /**
     * 追加若干项到数组结尾
     */
    public static <T> T[] append(T[] array1, T... array2) {
        T[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    public static byte[] append(byte[] array1, byte... array2) {
        byte[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * 添加若干项到数组开头
     */
    public static <T> T[] insert(T[] array1, T... array2) {
        T[] result = Arrays.copyOf(array2, array2.length + array1.length);
        System.arraycopy(array1, 0, result, array2.length, array1.length);
        return result;
    }

    public static String toString(Object... array) {
        StringBuffer stringBuffer = new StringBuffer();
        if (null != array && array.length > 0) {
            for (int i = 0; i < array.length - 1; i++) {
                stringBuffer.append(array[i]).append(',');
            }
            Object each = array[array.length - 1];
            if (each instanceof Map) {
                stringBuffer.append(Maps.toString((Map) each));
            } else if (each instanceof Collection) {
                stringBuffer.append(toString(((Collection) each).toArray()));
            } else {
                stringBuffer.append(each);
            }
        }
        return stringBuffer.toString();
    }

    public static Boolean contains(Object[] array, Object one) {
        for (Object each : array) {
            if ((null == each && null == one) //
                    || each.equals(one)) {
                return true;
            }
        }
        return false;
    }

    public static <T> T[] concat(T[] array1, T[] array2) {
        List<T> list = new ArrayList<T>(Arrays.asList(array1));
        list.addAll(Arrays.asList(array2));
        return list.toArray(array1);
    }

    public static <T> T[] concat(T[] array1, List<T> list) {
        return concat(array1, list.toArray(array1));
    }

    public static <T> Set<T> asSet(T... items) {
        return new HashSet<T>(Arrays.asList(items));
    }

    public static <T> T[] asArray(T... items) {
        return items;
    }

    public static byte[] subArray(byte[] input, int from, int length) {
        try {
            if (length + from > input.length) {
                length = input.length - from;
            }
            byte[] dest = new byte[length];
            System.arraycopy(input, from, dest, 0, length);
            return dest;
        } catch (Exception e) {
            throw new RuntimeException(input.length + ", " + from + ", " + length);
        }
    }

    public static Integer[] sequence(Integer from, Integer to, Integer step) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = from; i <= to; i = i + step) {
            list.add(new Integer(i));
        }
        return list.toArray(new Integer[0]);
    }

    public static Integer[] sequence(Integer from, Integer to) {
        return sequence(from, to, 1);
    }

    public static <T> List<T> asList(T... items) {
        return ListUtil.asList(items);
    }

    public static Integer lengthWithOutNull(Object... array) {
        Integer len = array.length;
        for (Object each : array) {
            if (null == each) {
                len--;
            }
        }
        return len;
    }

    public static Boolean isEmpty(Object[] array) {
        return null == array || array.length == 0;
    }

    public static Boolean isEmpty(String[] array) {
        return null == array || array.length == 0;
    }

    public static Object select(Object[] args, Integer index) {
        if (args.length > index) {
            return args[index];
        }
        return null;
    }

    public static String[] sort(String... array) {
        Arrays.sort(array);
        return array;
    }

    public static <T> T first(T[] array) {
        return (null == array || array.length < 1) ? null : array[0];
    }

    public static <T> List<T> remove(List<T> list, T... remove) {
        List<T> result = new ArrayList<T>();
        for (T each : list) {
            if (ArrayUtil.contains(remove, each)) {
                //
            } else {
                result.add(each);
            }
        }
        return new NiceToStringList(result);
    }
}