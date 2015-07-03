package cn.limw.summer.dao.hibernate.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.limw.summer.util.Errors;

import com.alibaba.fastjson.JSONArray;

/**
 * 搜索过滤器
 * @author li
 * @version 1 (2014年8月11日 上午9:25:37)
 * @since Java7
 */
public abstract class SearchFilter {
    private static final String ARG_KEY_PREFIX = "$";

    public static final String ENTITY_ALIAS = "entity";

    /**
     * 比较操作符
     */
    private String operator;

    /**
     * 递归时可能会引用
     */
    private SearchFilterTranslator translator;

    public SearchFilter() {}

    /**
     * 初始化一个带有指定操作符标记的SearchFilter
     */
    public SearchFilter(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public SearchFilterTranslator getTranslator() {
        return translator;
    }

    public SearchFilter setTranslator(SearchFilterTranslator translator) {
        this.translator = translator;
        return this;
    }

    /**
     * 生成不重复的参数key,设置参数值,返回参数key
     */
    public static String parameter(SearchCriteria searchCriteria, Object value) {
        String fieldValueKey = argKey(searchCriteria);
        setArg(searchCriteria, fieldValueKey, value);
        return fieldValueKey;
    }

    /**
     * 解析多个表达式
     */
    public List<String> translateItems(SearchCriteria searchCriteria, List<Map<String, Object>> items) {
        List<String> hqls = new ArrayList<String>();
        for (int i = 0; i < items.size(); i++) {
            Map<String, Object> item = items.get(i);
            String doTranslate = getTranslator().doTranslate(searchCriteria, item);
            if (null != doTranslate && !doTranslate.isEmpty()) {
                hqls.add(doTranslate);
            }
        }
        return hqls;
    }

    /**
     * 返回传入Entry的value(Map类型)的第一个Entry
     */
    public static Entry<?, ?> valueEntry(Entry<String, Object> entry) {
        return ((Map<?, ?>) entry.getValue()).entrySet().iterator().next();
    }

    /**
     * 检查属性名,不允许包含空格
     */
    public static String field(Object object) {
        String string = (String) object;
        if (string.trim().contains(" ")) {
            throw new IllegalArgumentException("FIELD_NAME_ERROR, name=" + object);
        }
        return string;
    }

    /**
     * 从JsonArray中得到多个表达式的Map
     */
    public static List<Map<String, Object>> getItems(JSONArray array) {
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < array.size(); i++) {
            Object object = array.get(i);
            Map<String, Object> map = (Map<String, Object>) object;
            if (null != object && !map.isEmpty()) {
                items.add(map);
            }
        }
        return items;
    }

    /**
     * 生成不重复的value-key
     */
    private static String argKey(SearchCriteria searchCriteria) {
        Map<String, Object> _args = searchCriteria.getArgs();
        for (int i = _args.size() + 1; true; i++) {//第n个参数直接从n开始判断重复
            String key = ARG_KEY_PREFIX + i;
            if (!_args.containsKey(key)) {
                return key;
            }
        }
    }

    /**
     * 设置参数值
     */
    private static void setArg(SearchCriteria searchCriteria, String fieldValueKey, Object value) {
        if (value instanceof JSONArray) {
            searchCriteria.setArg(fieldValueKey, Arrays.asList(((JSONArray) value).toArray()));
        } else {
            searchCriteria.setArg(fieldValueKey, value);
        }
    }

    /**
     * @see cn.limw.summer.dao.hibernate.search.SearchFilterTranslator#doTranslate(SearchCriteria, Map)
     */
    public String translate(SearchCriteria searchCriteria, Entry<String, Object> entry) {
        throw Errors.notImplemented();
    }
}