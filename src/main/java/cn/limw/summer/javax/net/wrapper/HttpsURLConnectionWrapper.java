package cn.limw.summer.javax.net.wrapper;

import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

/**
 * @author li
 * @version 1 (2014年11月27日 上午10:39:45)
 * @since Java7
 */
public class HttpsURLConnectionWrapper extends HttpsURLConnection {
    protected HttpsURLConnectionWrapper(URL url) {
        super(url);
    }

    public String getCipherSuite() {
        return null;
    }

    public Certificate[] getLocalCertificates() {
        return null;
    }

    public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
        return null;
    }

    public void disconnect() {}

    public boolean usingProxy() {
        return false;
    }

    public void connect() throws IOException {}
}