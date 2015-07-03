package cn.limw.summer.http;

import org.junit.Test;

import cn.limw.summer.http.Request;

/**
 * @author li
 * @version 1 (2014年10月21日 上午10:00:44)
 * @since Java7
 */
public class RequestTest {
    @Test
    public void asdf() {
        Request request = new Request();
        request.setUrl("t.cn");
        request.setField("a", new char[] { 'a', 'b', 'c' });
        request.setField("b", new int[] { 1, 2, 3 });
        System.err.println(request.execute());
    }
}