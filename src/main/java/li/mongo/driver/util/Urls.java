package li.mongo.driver.util;

import com.unblocked.support.util.ConvertUtil;
import com.unblocked.support.util.Errors;

/**
 * UrlUtil
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午2:50:01)
 */
public class Urls {
    private static final String ERROR_MESSAGE = "请核对传入的url 例 jdbc:mongo://127.0.0.1:27017/mongo_db_demo";

    /**
     * getHost
     */
    public static String getHost(String url) {
        url = url.replace("jdbc:mongo://", "");
        int index = url.indexOf(':');
        if (index > 0) {
            return url.substring(0, index);
        } else {
            int index2 = url.indexOf('/');
            return index2 > 0 ? url.substring(0, index2) : (String) Errors.cast(ERROR_MESSAGE);
        }
    }

    /**
     * getPort
     */
    public static int getPort(String url) {
        url = url.replace("jdbc:mongo://" + getHost(url), "");
        if (url.startsWith(":")) {
            int index = url.indexOf('/');
            return index > 0 ? ConvertUtil.toInt(url.substring(1, index), ERROR_MESSAGE) : (Integer) Errors.cast(ERROR_MESSAGE);
        } else {
            return url.startsWith("/") ? 27017 : (Integer) Errors.cast(ERROR_MESSAGE);
        }
    }

    /**
     * getDbName
     */
    public static String getDbName(String url) {
        int index = url.lastIndexOf('/');
        return index > 0 ? url.substring(index + 1) : (String) Errors.cast(ERROR_MESSAGE);
    }
}