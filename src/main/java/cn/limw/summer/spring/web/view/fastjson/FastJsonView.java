package cn.limw.summer.spring.web.view.fastjson;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * @author li
 * @version 1 (2014年7月28日 上午10:33:55)
 * @since Java7
 */
public class FastJsonView extends AbstractView {
    public static final String DEFAULT_CONTENT_TYPE = "application/json";

    public final static Charset UTF8 = Charset.forName("UTF-8");

    private boolean disableCaching = true;

    private boolean updateContentLength = false;

    private SerializeConfig serializeConfig;

    /* 实例代码块 */{
        super.setContentType(DEFAULT_CONTENT_TYPE);
        super.setExposePathVariables(false);
        this.serializeConfig = new SerializeConfig();
        this.serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }

    private String toJson(Object object) {
        SerializeWriter out = new SerializeWriter();
        try {
            JSONSerializer serializer = new JSONSerializer(out, this.serializeConfig);
            serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
            serializer.getValueFilters().add(new ValueFilter() {
                public Object process(Object object, String name, Object value) {
                    System.err.println("ValueFilter ###########");
                    System.err.println(object);
                    System.err.println(name);
                    System.err.println(value);
                    System.err.println("ValueFilter ###########");
                    return value;
                }
            });

            serializer.getPropertyFilters().add(new PropertyFilter() {
                public boolean apply(Object object, String name, Object value) {
                    System.err.println("PropertyFilter ###########");
                    System.err.println(object);
                    System.err.println(name);
                    System.err.println(value);
                    System.err.println("PropertyFilter ###########");
                    return true;
                }
            });
            serializer.getPropertyPreFilters().add(new PropertyPreFilter() {
                public boolean apply(JSONSerializer serializer, Object object, String name) {
                    System.err.println("PropertyPreFilter ###########");
                    System.err.println(object);
                    System.err.println(name);
                    System.err.println("PropertyPreFilter ###########");
                    return true;
                }
            });
            serializer.write(object);
            return out.toString();
        } catch (StackOverflowError e) {
            JSONSerializer serializer = new JSONSerializer(out, this.serializeConfig);
            serializer.config(SerializerFeature.DisableCircularReferenceDetect, false);
            serializer.write(object);
            return out.toString();
        } catch (Throwable e) {
            if (object instanceof Map) {
                Map map = (Map) object;
                Set set = map.entrySet();
                for (Object obj : set) {
                    Entry entry = (Entry) obj;
                    System.err.println("### 1" + entry.getKey() + " + " + entry.getKey().getClass());
                    System.err.println("### 2" + entry.getValue() + " + " + entry.getValue().getClass());
                }
            }
            throw new RuntimeException(object + " " + object.getClass(), e);
        } finally {
            out.close();
        }
    }

    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object value = this.filter(model);
        String text = toJson(value);
        byte[] bytes = text.getBytes(UTF8);
        OutputStream stream = this.updateContentLength ? createTemporaryOutputStream() : response.getOutputStream();
        stream.write(bytes);
        if (this.updateContentLength) {
            super.writeToResponse(response, (ByteArrayOutputStream) stream);
        }
    }

    private Map<String, Object> filter(Map<String, Object> model) {
        Map<String, Object> values = new HashMap<String, Object>();
        Set<Entry<String, Object>> set = model.entrySet();
        for (Entry<String, Object> entry : set) {
            Object value = entry.getValue();
            values.put(entry.getKey(), value);
        }
        return values;
    }

    protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
        setResponseContentType(request, response);
        response.setCharacterEncoding(UTF8.name());
        if (this.disableCaching) {
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
            response.addDateHeader("Expires", 1L);
        }
    }

    public void setDisableCaching(boolean disableCaching) {
        this.disableCaching = disableCaching;
    }

    public void setUpdateContentLength(boolean updateContentLength) {
        this.updateContentLength = updateContentLength;
    }
}