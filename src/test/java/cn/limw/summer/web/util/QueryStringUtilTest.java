package cn.limw.summer.web.util;

import org.junit.Test;

import cn.limw.summer.util.Maps;
import cn.limw.summer.web.util.QueryStringUtil;

/**
 * @author li
 * @version 1 (2014年7月18日 下午1:15:14)
 * @since Java7
 */
public class QueryStringUtilTest {
    @Test
    public void fromMap() {
        System.err.println(QueryStringUtil.fromMap(Maps.toMap("a", "1", "b", new Integer[] { 1, 2, 3 })));
    }
}