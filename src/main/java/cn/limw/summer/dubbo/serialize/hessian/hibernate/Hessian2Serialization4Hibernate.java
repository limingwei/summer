package cn.limw.summer.dubbo.serialize.hessian.hibernate;

import java.io.IOException;
import java.io.OutputStream;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.alibaba.dubbo.common.serialize.support.hessian.Hessian2Serialization;

/**
 * @author li
 * @version 1 (2014年6月26日 下午5:32:26)
 * @since Java7
 */
public class Hessian2Serialization4Hibernate extends Hessian2Serialization {
    public byte getContentTypeId() {
        return 11;
    }

    public ObjectOutput serialize(URL url, OutputStream out) throws IOException {
        return new Hessian2ObjectOutput4Hibernate(out);
    }
}