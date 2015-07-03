package cn.limw.summer.java.collection.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import cn.limw.summer.java.collection.util.CollectionUtil;

/**
 * @author li
 * @version 1 (2014年12月5日 下午3:20:45)
 * @since Java7
 */
public class CollectionUtilTest {
    @Test
    public void last() {
        Assert.assertEquals(4, CollectionUtil.last(Arrays.asList(2, 3, 4)));
        Assert.assertEquals(5, CollectionUtil.last(Arrays.asList(1, 2, 3, 4, 5)));
    }

    @Test
    public void first() {
        Assert.assertEquals(2, CollectionUtil.first(Arrays.asList(2, 3, 4)));
        Assert.assertEquals(1, CollectionUtil.first(Arrays.asList(1, 2, 3, 4, 5)));
    }
}