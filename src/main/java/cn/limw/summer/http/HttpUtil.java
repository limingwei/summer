package cn.limw.summer.http;

import java.util.Map;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * Https
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月16日 下午1:53:39)
 */
public class HttpUtil {
    private static final Logger log = Logs.slf4j();

    public static Response get(String url) {
        log.info("get() url={}", url);
        Request request = new Request();
        request.setUrl(url);
        return request.execute();
    }

    public static Response post(String url, byte[] data) {
        return post(url, null, data);
    }

    public static Response post(String url) {
        return post(url, null, null);
    }

    public static Response post(String url, Map fields) {
        return post(url, fields, null);
    }

    /**
     * 有 fields 时 不会用 data
     */
    public static Response post(String url, Map fields, byte[] data) {
        Request request = new Request();
        request.setUrl(url);
        request.setMethod(Request.POST);
        if (null != fields) {
            request.setFields(fields);
        } else {
            request.setData(data);
        }
        return request.execute();
    }
}