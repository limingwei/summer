package cn.limw.summer.mongo.driver.util;

/**
 * UrlUtil
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午2:50:01)
 */
public class UrlUtil {
    public static String getDbName(String url) {
        return "mongo_db_demo";
    }

    public static String getHost(String url) {
        return "127.0.0.1";
    }

    public static int getPort(String url) {
        return 27017;
    }
}