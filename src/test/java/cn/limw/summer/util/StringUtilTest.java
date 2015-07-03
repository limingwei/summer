package cn.limw.summer.util;

import org.junit.Assert;
import org.junit.Test;

import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年11月10日 下午1:51:18)
 * @since Java7
 */
public class StringUtilTest {
    @Test
    public void substring() {
        Assert.assertEquals("1", StringUtil.substring("123", 0, 1));
        Assert.assertEquals("12", StringUtil.substring("123", 0, 2));
        Assert.assertEquals("2", StringUtil.substring("123", 1, 2));

        Assert.assertEquals("3", StringUtil.substring("123", 0, -1));
        Assert.assertEquals("23", StringUtil.substring("123", 0, -2));
        Assert.assertEquals("12", StringUtil.substring("123", 1, -2));
    }
}