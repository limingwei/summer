package cn.limw.summer.dao.hibernate.search;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;

import cn.limw.summer.dao.hibernate.AbstractDao;
import cn.limw.summer.java.collection.FixedSizeLinkedHashMap;
import cn.limw.summer.util.ArrayUtil;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2014年8月15日 下午3:46:51)
 * @since Java7
 */
public class SearchUtil {
    private static final Logger log = Logs.slf4j();

    private static final Map<Set<String>, Set<String>> CACHE_MAP = new FixedSizeLinkedHashMap<Set<String>, Set<String>>(100);

    public static Set<String> fieldsToFetch(Set<String> fields, AbstractDao<?, ?> dao, Class<?> entityType) {
        Set<String> fetch = CACHE_MAP.get(fields);
        if (null == fetch) {
            CACHE_MAP.put(fields, fetch = doFieldsToFetch(fields, dao, entityType));
        }

        log.debug("fields={}, fetch={}", fields, fetch);
        return fetch;
    }

    /**
     * 传入要查询的所有字段,返回需要fetch的属性, 一个对象多个属性 只需要join一次
     */
    public static Set<String> doFieldsToFetch(Set<String> fields, AbstractDao<?, ?> dao, Class<?> entityType) {
        Set<String> fetch = new HashSet<String>();
        if (null == fields || fields.isEmpty()) { // TODO 如不定义返回列,则仅返回id ?
            fields = ArrayUtil.asSet(dao.getIdField(entityType));
        }
        for (String field : fields) {
            Field _field = getField(entityType, field);
            if (null == _field) {
                //没有指定的属性
            } else if (Mirrors.isBasicType(_field.getType())) {
                fetch.addAll(fetchFields(field));
            } else {
                fetch.add(field);//如果是对象类型
            }
        }
        return fetch;
    }

    private static List<String> fetchFields(String fieldName) {
        List<String> fetchFields = new ArrayList<String>();
        int index = fieldName.lastIndexOf('.');
        if (index > 0) {
            String subField = fieldName.substring(0, index);
            fetchFields.add(subField);
        }
        return fetchFields;
    }

    private static Field getField(Class<?> type, String fieldName) {
        try {
            int index = fieldName.indexOf((int) '.');
            if (index > 0) {
                String name = fieldName.substring(0, index);
                String name2 = fieldName.substring(index + 1);
                return getField(Mirrors.getField(type, name).getType(), name2);
            } else {
                return Mirrors.getField(type, fieldName);
            }
        } catch (Exception e) {
            log.error("getField error " + e, e);
            return null;
        }
    }
}