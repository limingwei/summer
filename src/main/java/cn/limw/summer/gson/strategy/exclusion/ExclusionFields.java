package cn.limw.summer.gson.strategy.exclusion;

import cn.limw.summer.util.ArrayUtil;
import cn.limw.summer.util.StringUtil;

import com.google.gson.FieldAttributes;

/**
 * 不序列化指定属性
 * @author li
 * @version 1 (2014年12月4日 下午2:56:59)
 * @since Java7
 */
public class ExclusionFields extends AbstractExclusionStrategy {
    private Class<?> type;

    private String[] fields;

    public ExclusionFields(Class<?> type, String[] fields) {
        this.type = type;
        this.fields = fields;
    }

    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getDeclaringClass().isAssignableFrom(type) && ArrayUtil.contains(fields, fieldAttributes.getName());
    }

    public String toString() {
        return "ExclusionFields{" + type.getName() + ",[" + StringUtil.join(",", fields) + "]}";
    }
}