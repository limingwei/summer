package builder.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Mould 模版
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年1月7日 上午10:02:57)
 */
public class Mould {
    private Map<String, Object> map = new HashMap<String, Object>();

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Object get(String key) {
        return this.map.get(key);
    }
}