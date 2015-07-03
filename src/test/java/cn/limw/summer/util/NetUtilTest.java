package cn.limw.summer.util;

import org.junit.Test;

import cn.limw.summer.util.NetUtil;

/**
 * @author li
 * @version 1 (2014年12月10日 下午4:01:08)
 * @since Java7
 */
public class NetUtilTest {
    @Test
    public void reachable() {
        System.err.println(NetUtil.reachable("t.tt222", 10));
        System.err.println(NetUtil.reachable("t.tt", 20));
        System.err.println(NetUtil.reachable("t.tt", 30));
        System.err.println(NetUtil.reachable("t.tt", 40));
        System.err.println(NetUtil.reachable("t.tt", 50));
        System.err.println(NetUtil.reachable("t.tt", 60));
        System.err.println(NetUtil.reachable("t.tt", 70));
        System.err.println(NetUtil.reachable("t.tt", 80));
    }
}