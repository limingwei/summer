package cn.limw.summer.xstream.converter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * TimeConverter
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月13日 下午12:53:22)
 */
public class TimeConverter implements Converter {
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public boolean canConvert(Class type) {
        return type.equals(Timestamp.class) || type.equals(java.sql.Date.class) || type.equals(java.util.Date.class);
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        if (source instanceof Timestamp) {
            writer.setValue(TIMESTAMP_FORMAT.format((Timestamp) source));
        } else if (source instanceof java.sql.Date) {
            writer.setValue(DATE_FORMAT.format((java.sql.Date) source));
        } else if (source instanceof java.util.Date) {
            writer.setValue(DATE_FORMAT.format((java.util.Date) source));
        }
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        throw new RuntimeException();
    }
}