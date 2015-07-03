package cn.limw.summer.spring.core.io.util;

import org.springframework.core.io.Resource;

import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年4月20日 下午2:11:10)
 * @since Java7
 */
public class ResourceUtil {
    public static String toString(Resource[] resources) {
        int length = resources.length;
        String[] strings = new String[length];
        for (int i = 0; i < length; i++) {
            Resource resource = resources[i];
            strings[i] = resource + "(" + resource.getFilename() + ")";
        }
        return "[" + StringUtil.join(", ", strings) + "]";
    }
}