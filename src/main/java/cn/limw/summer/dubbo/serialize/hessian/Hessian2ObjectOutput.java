package cn.limw.summer.dubbo.serialize.hessian;

import java.io.IOException;
import java.io.OutputStream;

import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import com.alibaba.com.caucho.hessian.io.SerializerFactory;
import com.alibaba.dubbo.common.serialize.ObjectOutput;

/**
 * @author li
 * @version 1 (2015年2月10日 上午9:45:19)
 * @since Java7
 */
public class Hessian2ObjectOutput implements ObjectOutput {
    private final Hessian2Output hessian2Output;

    public Hessian2Output getHessian2Output() {
        return hessian2Output;
    }

    public Hessian2ObjectOutput(OutputStream outputStream, SerializerFactory serializerFactory) {
        hessian2Output = new Hessian2Output(outputStream);
        hessian2Output.setSerializerFactory(serializerFactory);
    }

    public void writeObject(Object obj) throws IOException {
        getHessian2Output().writeObject(obj);
    }

    public void writeBool(boolean v) throws IOException {
        getHessian2Output().writeBoolean(v);
    }

    public void writeByte(byte v) throws IOException {
        getHessian2Output().writeInt(v);
    }

    public void writeShort(short v) throws IOException {
        getHessian2Output().writeInt(v);
    }

    public void writeInt(int v) throws IOException {
        getHessian2Output().writeInt(v);
    }

    public void writeLong(long v) throws IOException {
        getHessian2Output().writeLong(v);
    }

    public void writeFloat(float v) throws IOException {
        getHessian2Output().writeDouble(v);
    }

    public void writeDouble(double v) throws IOException {
        getHessian2Output().writeDouble(v);
    }

    public void writeBytes(byte[] b) throws IOException {
        getHessian2Output().writeBytes(b);
    }

    public void writeBytes(byte[] b, int off, int len) throws IOException {
        getHessian2Output().writeBytes(b, off, len);
    }

    public void writeUTF(String v) throws IOException {
        getHessian2Output().writeString(v);
    }

    public void flushBuffer() throws IOException {
        getHessian2Output().flushBuffer();
    }
}