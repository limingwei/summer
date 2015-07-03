package cn.limw.summer.spring.web.view.jackson;

import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * 扩展了 MappingJacksonJsonView 支持更多配置
 * @author li
 * @version 1 (2014年7月31日 下午4:33:55)
 * @since Java7
 */
public class JacksonView extends MappingJacksonJsonView {
    public JacksonView() {
        setDateFormat("yyyy-MM-dd HH:mm:ss");
        setWriteNullProperties(false);
        setWriteEmptyJsonArrays(false);
    }

    /**
     * 设置Json转换时的时间/日期格式
     * @param dateFormat 默认是 yyyy-MM-dd HH:mm:ss
     */
    public void setDateFormat(String dateFormat) {
        getObjectMapper().setDateFormat(new SimpleDateFormat(dateFormat));
    }

    /**
     * 是否写空属性
     * @param writeNullProperties 为True时打印空属性 默认为false
     */
    public void setWriteNullProperties(Boolean writeNullProperties) {
        getObjectMapper().configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, writeNullProperties);
    }

    /**
     * 是否写空集合/数组
     * @param writeEmptyJsonArrays 为True时打印空集合/数组 默认为false
     */
    public void setWriteEmptyJsonArrays(Boolean writeEmptyJsonArrays) {
        getObjectMapper().configure(SerializationConfig.Feature.WRITE_EMPTY_JSON_ARRAYS, writeEmptyJsonArrays);
    }
}