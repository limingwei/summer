package cn.limw.summer.java.util;

import java.io.IOException;
import java.io.Reader;

/**
 * @author li
 * @version 1 (2015年3月2日 下午6:33:23)
 * @since Java7
 */
public class Properties extends AbstractProperties {
    private static final long serialVersionUID = -4440628129739689588L;

    private String name = "Properties.defaultName";

    public Properties() {}

    public Properties(Reader reader, String name) {
        this.name = name;
        try {
            load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized String toString() {
        return "name=" + name + ", " + super.toString();
    }
}