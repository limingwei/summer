package cn.limw.summer.gson.adapter;

import java.io.IOException;

import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.collection.spi.PersistentCollection;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * @author li
 * @version 1 (2015年5月7日 上午10:58:19)
 * @since Java7
 */
public class PersistentSetAdapter extends TypeAdapter<PersistentCollection> {
    protected static final PersistentCollectionAdapter INSTANCE = new PersistentCollectionAdapter();

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            return (PersistentSet.class.isAssignableFrom(type.getRawType()) ? (TypeAdapter<T>) PersistentCollectionAdapter.INSTANCE : null);
        }
    };

    public PersistentCollection read(JsonReader in) throws IOException {
        throw new UnsupportedOperationException("Not supported");
    }

    public void write(JsonWriter out, PersistentCollection value) throws IOException {
        out.nullValue();
    }
}