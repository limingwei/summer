package cn.limw.summer.javax.net.wrapper;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author li
 * @version 1 (2014年11月27日 上午10:36:52)
 * @since Java7
 */
public class URLConnectionWrapper extends URLConnection {
    protected URLConnectionWrapper(URL url) {
        super(url);
    }

    public void connect() throws IOException {}
}