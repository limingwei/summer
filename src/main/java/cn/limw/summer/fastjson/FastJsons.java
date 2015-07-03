package cn.limw.summer.fastjson;

import java.util.Collection;

import cn.limw.summer.fastjson.serializer.filter.BasicTypePropertyOnlyFilter;
import cn.limw.summer.fastjson.serializer.filter.HibernatePropertyFilter;
import cn.limw.summer.fastjson.serializer.filter.PropertyNameFilter;
import cn.limw.summer.util.ListUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author li
 * @version 1 (2015年3月16日 上午10:23:12)
 * @since Java7
 */
public class FastJsons {
    public static final PropertyFilter BASIC_TYPE_PROPERTY_ONLY_FILTER = BasicTypePropertyOnlyFilter.INSTANCE;

    public static String toJson(Object source, SerializeFilter... serializeFilters) {
        try {
            SerializeWriter out = new SerializeWriter();
            JSONSerializer serializer = new JSONSerializer(out);
            serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
            serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
            serializer.setDateFormat("yyyy-MM-dd HH:mm:ss");
            serializer.getPropertyFilters().add(new PropertyNameFilter("password"));
            serializer.getPropertyFilters().add(HibernatePropertyFilter.INSTANCE);
            serializer.getPropertyFilters().addAll((Collection) ListUtil.asList(serializeFilters));
            serializer.write(source);
            return out.toString();
        } catch (Throwable e) {
            throw new RuntimeException("JsonUtil.toJson error source=" + source, e);
        }
    }

    public static <T> T fromJson(Class<T> clazz, String json) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> T basicTypePropertyOnly(T entity) {
        return (T) FastJsons.fromJson(entity.getClass(), FastJsons.toJson(entity, FastJsons.BASIC_TYPE_PROPERTY_ONLY_FILTER));
    }
}