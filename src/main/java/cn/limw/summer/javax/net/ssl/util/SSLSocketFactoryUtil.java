package cn.limw.summer.javax.net.ssl.util;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * @author li
 * @version 1 (2014年11月27日 上午9:47:44)
 * @since Java7
 */
public class SSLSocketFactoryUtil {
    public static SSLSocketFactory getSocketFactory(TrustManager[] trustManagers) {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagers, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}