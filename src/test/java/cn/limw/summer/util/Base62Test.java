package cn.limw.summer.util;

import org.junit.Test;

import cn.limw.summer.util.Base62;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年4月28日 上午10:11:53)
 * @since Java7
 */
public class Base62Test {
    @Test
    public void base62() {
        for (int i = 0; i < 1000; i++) {
            System.err.println(i + " -> " + Base62.toBase62(i) + " -> " + Base62.fromBase62(Base62.toBase62(i)));
        }
        for (int i = 0; i < 10; i++) {
            String str = StringUtil.dup("Z", i);
            long decode = Base62.fromBase62(str);
            System.err.println(str + " -> " + decode + "=" + (decode / 10000) + "万" + "=" + (decode / 10000 / 10000) + "亿");
        }
    }
}