package cn.limw.summer.util;

import org.junit.Assert;
import org.junit.Test;

import cn.limw.summer.util.ArrayUtil;

/**
 * @author li
 * @version 1 (2014年7月18日 上午9:17:20)
 * @since Java7
 */
public class ArrayUtilTest {
    @Test
    public void isEmpty() {
        Assert.assertEquals(0, new String[] {}.length);
        Assert.assertTrue(ArrayUtil.isEmpty(new String[] {}));
    }

    @Test
    public void testToString() {
        System.err.println(ArrayUtil.toString(1, 2, 3, 4, 5));
        System.err.println(ArrayUtil.toString((Object) null));
        System.err.println(ArrayUtil.toString((Object[]) null));
        System.err.println(ArrayUtil.toString(1));
        System.err.println(ArrayUtil.toString(new Object[0]));
        System.err.println(ArrayUtil.toString(new Object[] { 1, null, "a", 'b' }));
    }

    @Test
    public void contains() {
        Assert.assertTrue(ArrayUtil.contains(new String[] { "a" }, "a"));
        Assert.assertTrue(ArrayUtil.contains(new String[] { "a", "b", "c" }, "b"));
        Assert.assertTrue(ArrayUtil.contains(new String[] { "a", "b", "c" }, "c"));
        Assert.assertFalse(ArrayUtil.contains(new String[] { "a" }, "d"));
        Assert.assertFalse(ArrayUtil.contains(new String[] { "a", "b", "c" }, "e"));
        Assert.assertFalse(ArrayUtil.contains(new String[] { "a", "b", "c" }, "f"));
    }
}