package li.mongo.driver.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import li.mongo.driver.MongoPreparedStatement;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.unblocked.support.collection.wrapper.EntryWrapper;
import com.unblocked.support.collection.wrapper.IteratorWrapper;
import com.unblocked.support.collection.wrapper.ListWrapper;
import com.unblocked.support.collection.wrapper.MapWrapper;
import com.unblocked.support.collection.wrapper.SetWrapper;

/**
 * Term extends BasicDBObject
 * 
 * @author li
 * @version 1 2014年3月12日上午9:23:08
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Term extends BasicDBObject implements Map<String, Object> {
    public static final String NOT = "$not";

    private static final long serialVersionUID = 8860200473708851637L;

    private MongoPreparedStatement preparedStatement;

    public Term() {
        super();
    }

    /**
     * 这里不宜传入Term
     */
    public Term(Map<String, Object> map) {
        super(map);
    }

    public Term(String key, Object value) {
        super(key, value);
    }

    /**
     * 传入当前的PreparedStatement,解析结构时使用的PreparedStatement不是这个,但结构相同
     */
    public Term setPreparedStatement(MongoPreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
        return this;
    }

    /**
     * 会解包Holder值
     */
    public Set<Entry<String, Object>> entrySet() {
        return unWrapSet(super.entrySet());
    }

    /**
     * 会解包Holder值
     */
    public Object get(String key) {
        return unWrapHolder(super.get(key));
    }

    /**
     * 如果值是Holder则解包之
     */
    private Object unWrapHolder(Object _value) {
        if (null == _value) {
            return null;
        } else if (_value instanceof Boolean || _value instanceof String || _value instanceof Number || _value instanceof ObjectId) {
            return _value;
        } else if (_value instanceof Like) {
            Pattern pattern = Pattern.compile((String) unWrapHolder(((Like) _value).getValue()), Pattern.CASE_INSENSITIVE);
            return ((Like) _value).getNot() ? new BasicDBObject(NOT, pattern) : pattern;// Like
        } else if (_value instanceof Map) {
            return unWrapMap((Map) _value);
        } else if (_value instanceof List) {
            return unWrapList((List) _value);
        } else if (_value instanceof Set) {
            return unWrapSet((Set) _value);
        } else if (_value instanceof Entry) {
            return unWrapEntry((Entry) _value);
        } else if (_value.getClass().isArray()) {
            return unWrapArray((Object[]) _value);
        } else if (_value instanceof Holder) {
            return preparedStatement.getParameter(((Holder) _value).getIndex());
        } else {
            throw new RuntimeException("不支持的数据类型 " + _value + " " + _value.getClass());
        }
    }

    private Object[] unWrapArray(Object[] _value) {
        Object[] newArray = new Object[_value.length];
        for (int i = 0; i < _value.length; i++) {
            newArray[i] = unWrapHolder(_value[i]);
        }
        return newArray;
    }

    private Set unWrapSet(Set set) {
        return new SetWrapper(set) {
            public Iterator iterator() {
                return unWrapIterator(super.iterator());
            }
        };
    }

    private List unWrapList(List _value) {
        return new ListWrapper(_value) {
            public Iterator iterator() {
                return unWrapIterator(super.iterator());
            }
        };
    }

    private Iterator unWrapIterator(Iterator iterator) {
        return new IteratorWrapper(iterator) {
            public Object next() {
                return unWrapHolder(super.next());
            }
        };
    }

    private Map unWrapMap(Map map) {
        return new MapWrapper(map) {
            public Set entrySet() {
                return unWrapSet(super.entrySet());
            }
        };
    }

    private Entry unWrapEntry(Entry entry) {
        return new EntryWrapper(entry) {
            public Object getValue() {
                return unWrapHolder(super.getValue());
            }
        };
    }
}