package cn.limw.summer.javax.net.wrapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author li
 * @version 1 (2014年11月27日 上午10:38:27)
 * @since Java7
 */
public class HttpURLConnectionWrapper extends HttpURLConnection {
    protected HttpURLConnectionWrapper(URL u) {
        super(u);
    }

    public void disconnect() {}

    public boolean usingProxy() {
        return false;
    }

    public void connect() throws IOException {}
}