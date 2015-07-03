package cn.limw.summer.gson.strategy.exclusion;

import cn.limw.summer.gson.Gsons;
import cn.limw.summer.gson.adapter.HibernateProxyAdapter;
import cn.limw.summer.gson.adapter.PersistentCollectionAdapter;
import cn.limw.summer.spring.web.servlet.Mvcs;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author li
 * @version 1 (2014年11月12日 下午12:36:17)
 * @since Java7
 */
public abstract class AbstractExclusionStrategy implements ExclusionStrategy {
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return false;
    }

    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

    /**
     * 不序列化指定属性
     */
    public AbstractExclusionStrategy exclusion(Class<?> type, String... fields) {
        return new ComplexExclusionStrategy(this, Gsons.exclusion(type, fields));
    }

    /**
     * 仅序列化基本类型属性
     */
    public ComplexExclusionStrategy exclusionExceptPrimitive(Class<?> type) {
        return new ComplexExclusionStrategy(this, Gsons.exclusionExceptPrimitive(type));
    }

    /**
     * 仅序列化指定属性
     */
    public ComplexExclusionStrategy exclusionExcept(Class<?> type, String... fields) {
        return new ComplexExclusionStrategy(this, Gsons.exclusionExcept(type, fields));
    }

    public void setToRequest() {
        Mvcs.setRequest("exclusion_strategy", this);
    }

    public Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.registerTypeAdapterFactory(HibernateProxyAdapter.FACTORY);
        gsonBuilder.registerTypeAdapterFactory(PersistentCollectionAdapter.FACTORY);
        gsonBuilder.setExclusionStrategies(this);
        return gsonBuilder.create();
    }
}