package cn.limw.summer.json.writer;

import cn.limw.summer.json.JsonWriter;

/**
 * @author li
 * @version 1 (2015年7月1日 下午6:47:33)
 * @since Java7
 */
public class StringJsonWriter implements JsonWriter {
    private String json = "";

    public String getString() {
        return json;
    }

    public void write(String string) {
        json += string;
    }
}