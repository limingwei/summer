package cn.limw.summer.java.net.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author li
 * @version 1 (2014年11月26日 上午10:55:46)
 * @since Java7
 */
public class NetUrlUtil {
    public static String encode(String url, String enc) {
        try {
            return URLEncoder.encode(url, enc);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encode(String url) {
        if (null == url) {
            return null;
        }
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return URLEncoder.encode(url);
        }
    }

    public static String decode(String url) {
        return decode(url, "UTF-8");
    }

    public static String decode(String url, String enc) {
        if (null == url) {
            return null;
        }
        try {
            return URLDecoder.decode(url, enc);
        } catch (UnsupportedEncodingException e) {
            return URLDecoder.decode(url);
        }
    }

    public static URL newUrl(String spec) {
        try {
            return new URL(spec);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream openStream(String spec) {
        return openStream(newUrl(spec));
    }

    public static InputStream openStream(URL url) {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}