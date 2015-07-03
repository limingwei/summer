package cn.limw.summer.dubbo.serialize.gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import com.alibaba.dubbo.common.serialize.ObjectInput;

/**
 * @author li
 * @version 1 (2014年8月19日 下午12:06:40)
 * @since Java7
 */
public class GsonObjectInput implements ObjectInput {
    public GsonObjectInput(InputStream input) {}

    public boolean readBool() throws IOException {
        return false;
    }

    public byte readByte() throws IOException {
        return 0;
    }

    public short readShort() throws IOException {
        return 0;
    }

    public int readInt() throws IOException {
        return 0;
    }

    public long readLong() throws IOException {
        return 0;
    }

    public float readFloat() throws IOException {
        return 0;
    }

    public double readDouble() throws IOException {
        return 0;
    }

    public String readUTF() throws IOException {
        return null;
    }

    public byte[] readBytes() throws IOException {
        return null;
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return null;
    }

    public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
        return null;
    }

    public <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException {
        return null;
    }
}