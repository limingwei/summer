package cn.limw.summer.spring.web.view.gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.view.AbstractView;

import cn.limw.summer.gson.adapter.HibernateProxyAdapter;
import cn.limw.summer.gson.adapter.PersistentCollectionAdapter;
import cn.limw.summer.gson.adapter.PersistentSetAdapter;
import cn.limw.summer.gson.adapter.SqlTimeAdapter;
import cn.limw.summer.util.Errors;
import cn.limw.summer.util.Files;
import cn.limw.summer.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author li
 * @version 1 (2014年8月15日 上午11:59:53)
 * @since Java7
 */
public class GsonView extends AbstractView implements InitializingBean {
    public static final String DEFAULT_CONTENT_TYPE = "application/json; charset=utf-8";

    private List<String> ignoreFields = new ArrayList<String>();

    private Gson defaultGson;

    private String dateFormat = "yyyy-MM-dd HH:mm:ss";

    private Set<String> fields;

    public void afterPropertiesSet() throws Exception {
        super.setContentType(DEFAULT_CONTENT_TYPE);
        GsonBuilder gsonBuilder = gsonBuilder();
        setDefaultGson(gsonBuilder.create());
    }

    protected GsonBuilder gsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(getDateFormat());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.registerTypeAdapterFactory(SqlTimeAdapter.FACTORY);
        gsonBuilder.registerTypeAdapterFactory(HibernateProxyAdapter.FACTORY);
        gsonBuilder.registerTypeAdapterFactory(PersistentSetAdapter.FACTORY);
        gsonBuilder.registerTypeAdapterFactory(PersistentCollectionAdapter.FACTORY);
        return gsonBuilder;
    }

    public Gson getDefaultGson() {
        return defaultGson;
    }

    public void setDefaultGson(Gson defaultGson) {
        this.defaultGson = defaultGson;
    }

    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        fields = (Set<String>) request.getAttribute("fields");
        response.setContentType(DEFAULT_CONTENT_TYPE);
        String json = toJson(getDefaultGson(), model);
        Files.write(json.getBytes(), response.getOutputStream()); // Mvcs.write(json);   
    }

    public String toJson(Gson gson, Object object) {
        JsonElement jsonTree = gson.toJsonTree(object);
        JsonArray jsonArray = getList(jsonTree);
        if (null != jsonArray) {
            for (JsonElement jsonElement : jsonArray) {
                JsonObject each = jsonElement.getAsJsonObject();
                filter("", each);
            }
        }
        return gson.toJson(jsonTree);
    }

    public JsonArray getList(JsonElement jsonTree) {
        throw Errors.notImplemented();
    }

    private void filter(String keyPrefix, JsonObject each) {
        Set<Entry<String, JsonElement>> entrySet = each.entrySet();
        Iterator<Entry<String, JsonElement>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Entry<String, JsonElement> entry = iterator.next();
            String fieldName = entry.getKey();
            String fieldFullName = fieldFullName(keyPrefix, fieldName);
            JsonElement value = entry.getValue();

            if (value instanceof JsonObject) {//对象属性
                filterJsonObject(iterator, fieldFullName, value.getAsJsonObject());
            } else if (value instanceof JsonArray) {//集合属性
                filterJsonArray(iterator, fieldFullName, value.getAsJsonArray());
            } else {//基本属性
                filterBasicField(iterator, fieldName, fieldFullName);
            }
        }
    }

    private void filterBasicField(Iterator<Entry<String, JsonElement>> iterator, String fieldName, String fieldFullName) {
        if (ignoreFields.contains(fieldName)) {
            iterator.remove();
        } else if (ignore(fieldFullName, fieldName)) {
            iterator.remove();
        }
    }

    private void filterJsonArray(Iterator<Entry<String, JsonElement>> iterator, String fieldFullName, JsonArray jsonArray) {
        if (jsonArray.size() > 0) {
            JsonArray toRemove = new JsonArray();
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                filter(fieldFullName, jsonObject);
                if (jsonObject.entrySet().size() < 1) {//移除空对象
                    toRemove.add(jsonElement);
                }
            }
            for (JsonElement jsonElement : toRemove) {//移除空对象
                jsonArray.remove(jsonElement);
            }
            if (jsonArray.size() < 1) {//移除空对象
                iterator.remove();
            }
        } else {//移除空集合
            iterator.remove();
        }
    }

    private void filterJsonObject(Iterator<Entry<String, JsonElement>> iterator, String fieldFullName, JsonObject jsonObject) {
        filter(fieldFullName, jsonObject);//过滤下一级
        if (jsonObject.entrySet().size() < 1) {//移除空对象
            iterator.remove();
        }
    }

    private boolean ignore(String fieldFullName, String fieldName) {
        if (null == fields || fields.isEmpty()) {
            return false;
        }
        boolean ignore = true;
        for (String _field : fields) {
            if (fieldFullName.equals(_field)) {
                ignore = false;
            } else {
                String str = _field + ".";
                int index = fieldFullName.indexOf(str);
                if (index > -1) {//是一级属性
                    int fromIndex = index + str.length() + 1;
                    int index2 = fieldFullName.indexOf('.', fromIndex);
                    if (index2 > -1) {//是二级属性
                        int from3 = index2 + 1 + 1;
                        int index3 = fieldFullName.indexOf('.', from3);
                        if (index3 < 0 && fieldName.equals("id")) {//名为id且不是三级属性
                            ignore = false;//保留
                        } else {
                            ignore = true;
                        }
                    } else {
                        ignore = false;//保留
                    }
                }
            }
        }
        return ignore;
    }

    private static String fieldFullName(String preFix, String name) {
        if (StringUtil.isEmpty(preFix)) {
            return name;
        } else {
            return preFix + "." + name;
        }
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public List<String> getIgnoreFields() {
        return ignoreFields;
    }

    public void setIgnoreFields(List<String> ignoreFields) {
        this.ignoreFields = ignoreFields;
    }
}