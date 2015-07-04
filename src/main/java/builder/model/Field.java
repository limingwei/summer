package builder.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 表示对象的一个属性 表的一个列
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年1月8日 下午3:39:48)
 */
public class Field {
    private Map<String, Object> map = new HashMap<String, Object>();

    private Boolean isPk;

    public Object get(String key) {
        return this.map.get(key);
    }

    public Field set(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public Boolean getIsPk() {
        if (null == this.isPk) {
            return this.isPk = "PRI".equalsIgnoreCase((String) this.get("key"));
        }
        return this.isPk;
    }
}