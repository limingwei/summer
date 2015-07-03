package cn.limw.summer.mybatis.util;

import java.util.List;

import org.apache.ibatis.session.defaults.DefaultSqlSession.StrictMap;

/**
 * @author li
 * @version 1 (2015年5月20日 下午2:59:17)
 * @since Java7
 */
public class ParameterUtil {
    public static Object wrapCollection(Object object) {
        if (object instanceof List) {
            StrictMap<Object> map = new StrictMap<Object>();
            map.put("list", object);
            return map;
        } else if (object != null && object.getClass().isArray()) {
            StrictMap<Object> map = new StrictMap<Object>();
            map.put("array", object);
            return map;
        }
        return object;
    }
}