package cn.limw.summer.http;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Cookie存储区
 */
public class CookieStore {
    private List<HttpCookie> cookies = new ArrayList<HttpCookie>();

    /**
     * 存储一些Cookie
     */
    public void addAll(List<HttpCookie> cookies) {
        for (HttpCookie cookie : cookies) {
            add(cookie);
        }
    }

    /**
     * 存储一个Cookie,如有重复会替换
     */
    public void add(HttpCookie cookie) {
        for (HttpCookie each : cookies) {
            if (Util.sameExceptValue(cookie, each)) {
                each.setValue(cookie.getValue());
                return;
            }
        }
        cookies.add(cookie);
    }

    /**
     * 返回存储的所有Cookie
     */
    public List<HttpCookie> getCookies() {
        return this.cookies;
    }

    /**
     * 从CookieString中获取特定名称的Cookie的值
     */
    public static String getCookie(String cookies, String key) {
        String value = "";
        int start = cookies.indexOf(key);
        if (start > 0) {
            String temp = cookies.substring(start);
            value = temp.substring(temp.indexOf("=") + 1, temp.indexOf(";"));
        }
        return value;
    }

    /**
     * 从Header中提取Cookie
     */
    public static String getCookies(Map<String, List<String>> headerFields) {
        String cookies = "";
        List<String> Set_Cookie = headerFields.get("Set-Cookie");
        if (null != Set_Cookie) {
            for (String string : Set_Cookie) {
                cookies += string;
            }
        }
        return cookies;
    }
}