package cn.limw.summer.gson.strategy.exclusion;

import cn.limw.summer.util.ArrayUtil;
import cn.limw.summer.util.StringUtil;

import com.google.gson.FieldAttributes;

/**
 * 仅序列化指定属性
 * @author li
 * @version 1 (2014年12月4日 下午2:56:59)
 * @since Java7
 */
public class ExclusionExceptFields extends AbstractExclusionStrategy {
    private Class<?> type;

    private String[] fields;

    public ExclusionExceptFields(Class<?> type, String[] fields) {
        this.type = type;
        this.fields = fields;
    }

    /**
     * true 就是跳过 不序列化 指定属性应该返回false 其他类应该返回false
     */
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getDeclaringClass().isAssignableFrom(type) && !ArrayUtil.contains(fields, fieldAttributes.getName());
    }

    public String toString() {
        return "ExclusionExceptFields{" + type.getName() + ",[" + StringUtil.join(",", fields) + "]}";
    }
}