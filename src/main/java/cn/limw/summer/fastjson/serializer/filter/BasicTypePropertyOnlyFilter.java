package cn.limw.summer.fastjson.serializer.filter;

import cn.limw.summer.util.Mirrors;

import com.alibaba.fastjson.serializer.PropertyFilter;

/**
 * @author li
 * @version 1 (2015年3月16日 上午10:41:02)
 * @since Java7
 */
public class BasicTypePropertyOnlyFilter implements PropertyFilter {
    public static final BasicTypePropertyOnlyFilter INSTANCE = new BasicTypePropertyOnlyFilter();

    public boolean apply(Object object, String name, Object value) {
        return null == value || Mirrors.isBasicType(value.getClass());
    }
}