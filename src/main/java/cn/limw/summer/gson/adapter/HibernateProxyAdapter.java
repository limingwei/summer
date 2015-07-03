package cn.limw.summer.gson.adapter;

import java.io.IOException;

import org.hibernate.proxy.HibernateProxy;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * @author li
 * @version 1 (2014年8月19日 上午11:19:01)
 * @since Java7
 */
public class HibernateProxyAdapter extends TypeAdapter<HibernateProxy> {
    protected static final HibernateProxyAdapter INSTANCE = new HibernateProxyAdapter();

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            return HibernateProxy.class.isAssignableFrom(type.getRawType()) ? (TypeAdapter<T>) HibernateProxyAdapter.INSTANCE : null;
        }
    };

    public HibernateProxy read(JsonReader in) throws IOException {
        throw new UnsupportedOperationException("Not supported");
    }

    public void write(JsonWriter out, HibernateProxy value) throws IOException {
        out.nullValue();
    }
}