package cn.limw.summer.gson.strategy.exclusion;

import cn.limw.summer.util.Mirrors;

import com.google.gson.FieldAttributes;

/**
 * @author li
 * @version 1 (2014年12月4日 下午2:53:33)
 * @since Java7
 */
public class ExclusionExceptPrimitive extends AbstractExclusionStrategy {
    private Class<?> type;

    public ExclusionExceptPrimitive(Class<?> type) {
        this.type = type;
    }

    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getDeclaringClass().isAssignableFrom(type) && !Mirrors.isBasicType(fieldAttributes.getDeclaredClass());
    }

    public String toString() {
        return "ExclusionExceptPrimitive{" + type.getName() + "}";
    }
}