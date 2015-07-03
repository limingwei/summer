package cn.limw.summer.fastjson.serializer.filter;

import com.alibaba.fastjson.serializer.PropertyFilter;

/**
 * @author li
 * @version 1 (2014年11月20日 下午1:26:08)
 * @since Java7
 */
public class PropertyNameFilter implements PropertyFilter {
    private String[] propertyNames;

    public PropertyNameFilter(String... propertyNames) {
        this.propertyNames = propertyNames;
    }

    public boolean apply(Object object, String name, Object value) {
        for (String propertyName : propertyNames) {
            if (propertyName.equals(name)) {
                return false;
            }
        }
        return true;
    }
}