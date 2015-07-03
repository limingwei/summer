package cn.limw.summer.util;

import org.junit.Test;

import cn.limw.summer.util.Base26;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年4月28日 上午10:11:53)
 * @since Java7
 */
public class Base26Test {
    @Test
    public void base26() {
        for (int i = 1; i < 1000; i++) {
            System.err.println(i + " -> " + Base26.encodeNumber(i));
        }
        for (int i = 1; i < 10; i++) {
            String str = StringUtil.dup("z", i);
            long decode = Base26.decodeNumber(str);
            System.err.println(str + " -> " + decode + "=" + (decode / 10000) + "万" + "=" + (decode / 10000 / 10000) + "亿");
        }
    }
}