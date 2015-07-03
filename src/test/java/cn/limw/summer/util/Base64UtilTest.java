package cn.limw.summer.util;

import org.junit.Test;

import cn.limw.summer.util.Base64Util;

/**
 * @author li
 * @version 1 (2015年3月12日 下午3:18:12)
 * @since Java7
 */
public class Base64UtilTest {
    @Test
    public void asdf() {
        String content = "中文=+~!@#$%^&*()_[]\';:\"{}|/.asdf();,<>?\\";
        System.err.println(content);
        System.err.println(Base64Util.urlWellEncrypt(content));
        System.err.println(Base64Util.urlWellDecrypt(Base64Util.urlWellEncrypt(content)));
    }
}