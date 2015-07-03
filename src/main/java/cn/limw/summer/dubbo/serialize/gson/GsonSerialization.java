package cn.limw.summer.dubbo.serialize.gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.alibaba.dubbo.common.serialize.Serialization;

/**
 * @author li
 * @version 1 (2014年8月19日 下午12:05:20)
 * @since Java7
 */
public class GsonSerialization implements Serialization {
    public byte getContentTypeId() {
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public ObjectOutput serialize(URL url, OutputStream output) throws IOException {
        return new GsonObjectOutput(output);
    }

    public ObjectInput deserialize(URL url, InputStream input) throws IOException {
        return new GsonObjectInput(input);
    }
}