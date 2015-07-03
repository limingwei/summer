package cn.limw.summer.util;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;

import cn.limw.summer.fastjson.parser.deserializer.SqlTimeDeserializer;
import cn.limw.summer.java.collection.NiceToStringMap;
import cn.limw.summer.json.JsonMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Json工具类
 * @author 明伟
 * @version 1 (2014年6月15日 下午11:58:09)
 * @since Java7
 */
public class Jsons {
    private static final Logger log = Logs.slf4j();

    /**
     * 对象转json
     */
    public static String toJson(Object source) {
        try {
            return JSON.toJSONStringWithDateFormat(source, "yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);//用fastjson序列化
        } catch (Throwable e) {
            try {
                return new ObjectMapper().writeValueAsString(source); //用Jackson序列化
            } catch (Throwable e2) {
                log.error("Fastjson error", e);
                log.error("Jackson error", e2);
                return "{\"error\":\"" + e + ", " + e2 + "\"}"; //返回错误信息
            }
        }
    }

    public static byte[] toJsonByteArray(Object source) {
        return JSON.toJSONBytes(source);
    }

    public static Map toMap(String source) {
        try {
            return new NiceToStringMap(JSON.parseObject(source, Map.class));
        } catch (Exception e) {
            throw new RuntimeException("JsonToMapError source=" + source + " error=" + e.getMessage(), e);
        }
    }

    public static List<Map> toMapList(String source) {
        try {
            return JSON.parseArray(source, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("JsonToMapError " + source + " " + e.getMessage(), e);
        }
    }

    public static <T> List<T> toList(Class<T> type, String source) {
        try {
            return JSON.parseArray(source, type);
        } catch (Exception e) {
            throw new RuntimeException("JsonToMapError " + source + " " + e.getMessage(), e);
        }
    }

    public static <T> List<T> toList(Class<T> type, InputStream inputStream) {
        return toList(type, Files.toString(inputStream));
    }

    public static <T> T fromJson(Class<T> type, String json) {
        try {
            if (StringUtil.isEmpty(json)) {
                return null;
            }
            ParserConfig config = ParserConfig.getGlobalInstance();
            config.putDeserializer(Time.class, SqlTimeDeserializer.INSTANCE);
            return JSON.parseObject(json, (Type) type, config, JSON.DEFAULT_PARSER_FEATURE, new Feature[0]);
        } catch (Exception e) {
            throw new JSONException(type.getName() + ", " + json + ", " + e.getMessage(), e);
        }
    }

    public static String toMapJson(Object... items) {
        return toJson(Maps.toMap(items));
    }

    public static String[] toStringArray(String json) {
        if (null == json) {
            return null;
        } else if (StringUtil.isEmpty(json)) {
            return new String[0];
        } else {
            return JSON.parseArray(json, String.class).toArray(new String[0]);
        }
    }

    public static JsonMap toJsonMap(String json) {
        return new JsonMap(StringUtil.isEmpty(json) ? new HashMap<String, Object>() : JSON.parseObject(json, Map.class));
    }
}