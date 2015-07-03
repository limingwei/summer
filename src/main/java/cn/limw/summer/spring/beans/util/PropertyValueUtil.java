package cn.limw.summer.spring.beans.util;

import java.util.List;

import org.springframework.beans.PropertyValue;

/**
 * @author li
 * @version 1 (2014年12月11日 下午4:00:56)
 * @since Java7
 */
public class PropertyValueUtil {
    /**
     * @see org.springframework.beans.MutablePropertyValues#getPropertyValue(String)
     */
    public static PropertyValue getPropertyValue(List<PropertyValue> propertyValues, String propertyName) {
        for (PropertyValue pv : propertyValues) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }
}