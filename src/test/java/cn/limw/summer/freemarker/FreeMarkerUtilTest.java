package cn.limw.summer.freemarker;

import org.junit.Test;

import cn.limw.summer.freemarker.util.FreeMarkerUtil;
import cn.limw.summer.util.Maps;

/**
 * @author li
 * @version 1 (2014年9月29日 下午3:51:19)
 * @since Java7
 */
public class FreeMarkerUtilTest {
    @Test
    public void merge() {
        FreeMarkerUtil.merge("bbbbbbbb", Maps.toMap("a", "参数a"));
    }
}