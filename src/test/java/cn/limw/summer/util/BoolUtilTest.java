package cn.limw.summer.util;

import org.junit.Assert;
import org.junit.Test;

import cn.limw.summer.util.BoolUtil;

/**
 * @author li
 * @version 1 (2015年3月31日 上午9:53:20)
 * @since Java7
 */
public class BoolUtilTest {
    @Test
    public void isTrue() {
        Assert.assertTrue(BoolUtil.isTrue(true));
    }
}