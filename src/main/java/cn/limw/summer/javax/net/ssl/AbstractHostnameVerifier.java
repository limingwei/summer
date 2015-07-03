package cn.limw.summer.javax.net.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * @author li
 * @version 1 (2014年11月27日 上午9:44:05)
 * @since Java7
 */
public class AbstractHostnameVerifier implements HostnameVerifier {
    private Boolean verifyReturn = false;

    public AbstractHostnameVerifier() {}

    public AbstractHostnameVerifier(Boolean verifyReturn) {
        this.verifyReturn = verifyReturn;
    }

    public boolean verify(String hostname, SSLSession session) {
        return verifyReturn;
    }
}