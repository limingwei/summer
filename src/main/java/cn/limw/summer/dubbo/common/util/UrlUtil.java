package cn.limw.summer.dubbo.common.util;

import com.alibaba.dubbo.common.URL;

/**
 * @author li
 * @version 1 (2015年6月1日 上午9:49:01)
 * @since Java7
 */
public class UrlUtil {
    /**
     * @see com.alibaba.dubbo.common.URL#buildString(boolean, boolean, boolean, boolean, String...)
     */
    public static String toSimpleString(URL url) {
        StringBuilder buf = new StringBuilder();
        if (url.getProtocol() != null && url.getProtocol().length() > 0) {
            buf.append(url.getProtocol());
            buf.append("://");
        }

        String host = url.getIp();

        if (host != null && host.length() > 0) {
            buf.append(host);
            if (url.getPort() > 0) {
                buf.append(":");
                buf.append(url.getPort());
            }
        }
        String path;
        //        if (useService) {
        //            path = url.getServiceKey();
        //        } else {
        path = url.getPath();
        //        }
        if (path != null && path.length() > 0) {
            buf.append("/");
            buf.append(path);
        }
        return buf.toString();
    }
}