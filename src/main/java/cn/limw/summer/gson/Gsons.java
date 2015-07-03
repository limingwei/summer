package cn.limw.summer.gson;

import cn.limw.summer.gson.strategy.exclusion.AbstractExclusionStrategy;
import cn.limw.summer.gson.strategy.exclusion.ExclusionExceptFields;
import cn.limw.summer.gson.strategy.exclusion.ExclusionExceptPrimitive;
import cn.limw.summer.gson.strategy.exclusion.ExclusionFields;

import com.google.gson.Gson;

/**
 * @author li
 * @version 1 (2014年11月12日 上午11:58:04)
 * @since Java7
 */
public class Gsons {
    /**
     * 不序列化指定属性
     */
    public static AbstractExclusionStrategy exclusion(final Class<?> type, final String... fields) {
        return new ExclusionFields(type, fields);
    }

    /**
     * 仅序列化基本类型属性
     */
    public static AbstractExclusionStrategy exclusionExceptPrimitive(final Class<?> type) {
        return new ExclusionExceptPrimitive(type);
    }

    /**
     * 仅序列化指定属性
     */
    public static AbstractExclusionStrategy exclusionExcept(final Class<?> type, final String... fields) {
        return new ExclusionExceptFields(type, fields);
    }

    public static AbstractExclusionStrategy exclusionStrategy() {
        return new AbstractExclusionStrategy() {};
    }

    public static String toJson(Object target) {
        return new Gson().toJson(target);
    }
}