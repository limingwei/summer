package cn.limw.summer.spring.http.converter.jackson;

import java.text.SimpleDateFormat;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * 扩展 MappingJacksonHttpMessageConverter 可以配置 DateFormat
 * @author li
 * @version 1 (2014年7月31日 下午3:17:58)
 * @since Java7
 */
public class JacksonHttpMessageConverter extends MappingJacksonHttpMessageConverter {
    public JacksonHttpMessageConverter() {
        getObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 设置Json转换时的时间/日期格式
     * @param dateFormat 默认是 yyyy-MM-dd HH:mm:ss
     */
    public void setDateFormat(String dateFormat) {
        getObjectMapper().setDateFormat(new SimpleDateFormat(dateFormat));
    }
}