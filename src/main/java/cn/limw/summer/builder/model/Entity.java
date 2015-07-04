package cn.limw.summer.builder.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity 表示一个对象 对应一张表
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年1月8日 下午6:03:52)
 */
public class Entity {
    private Map<String, Object> map = new HashMap<String, Object>();

    private List<Field> fields;

    private Field pk;

    public Field getPk() {
        if (null == this.pk) {
            for (Field field : getFields()) {
                if (field.getIsPk()) {
                    return this.pk = field;
                }
            }
        }
        return this.pk;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Object get(String key) {
        return this.map.get(key);
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}