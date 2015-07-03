package cn.limw.summer.gson.adapter;

import java.io.IOException;
import java.sql.Time;

import cn.limw.summer.time.Clock;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * @author li
 * @version 1 (2015年1月29日 下午2:17:54)
 * @since Java7
 */
public class SqlTimeAdapter extends TypeAdapter<Time> {
    protected static final SqlTimeAdapter INSTANCE = new SqlTimeAdapter();

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            return (Time.class.isAssignableFrom(type.getRawType()) ? (TypeAdapter<T>) SqlTimeAdapter.INSTANCE : null);
        }
    };

    public void write(JsonWriter out, Time value) throws IOException {
        if (null != value) {
            out.value(Clock.when(value).toHH_MM_SS());
        }
    }

    public Time read(JsonReader in) throws IOException {
        throw new UnsupportedOperationException("Not supported");
    }
}