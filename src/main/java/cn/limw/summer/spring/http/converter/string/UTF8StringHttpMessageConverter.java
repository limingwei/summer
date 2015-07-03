package cn.limw.summer.spring.http.converter.string;

import java.nio.charset.Charset;

import org.springframework.http.converter.StringHttpMessageConverter;

/**
 * @author li
 * @version 1 (2015年2月2日 下午3:33:45)
 * @since Java7
 */
public class UTF8StringHttpMessageConverter extends StringHttpMessageConverter {
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    public UTF8StringHttpMessageConverter() {
        super(UTF_8);
    }
}