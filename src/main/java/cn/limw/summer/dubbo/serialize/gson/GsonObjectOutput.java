package cn.limw.summer.dubbo.serialize.gson;

import java.io.IOException;
import java.io.OutputStream;

import com.alibaba.dubbo.common.serialize.ObjectOutput;

/**
 * @author li
 * @version 1 (2014年8月19日 下午12:06:48)
 * @since Java7
 */
public class GsonObjectOutput implements ObjectOutput {
    public GsonObjectOutput(OutputStream output) {}

    public void writeBool(boolean v) throws IOException {}

    public void writeByte(byte v) throws IOException {}

    public void writeShort(short v) throws IOException {}

    public void writeInt(int v) throws IOException {}

    public void writeLong(long v) throws IOException {}

    public void writeFloat(float v) throws IOException {}

    public void writeDouble(double v) throws IOException {}

    public void writeUTF(String v) throws IOException {}

    public void writeBytes(byte[] v) throws IOException {}

    public void writeBytes(byte[] v, int off, int len) throws IOException {}

    public void flushBuffer() throws IOException {}

    public void writeObject(Object obj) throws IOException {}
}