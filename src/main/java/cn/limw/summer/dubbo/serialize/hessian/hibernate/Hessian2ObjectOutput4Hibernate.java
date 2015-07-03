package cn.limw.summer.dubbo.serialize.hessian.hibernate;

import java.io.OutputStream;

import cn.limw.summer.dubbo.serialize.hessian.Hessian2ObjectOutput;

/**
 * @author li
 * @version 1 (2014年6月26日 下午5:36:38)
 * @since Java7
 * @see com.alibaba.dubbo.common.serialize.support.hessian.Hessian2ObjectOutput
 */
public class Hessian2ObjectOutput4Hibernate extends Hessian2ObjectOutput {
    public Hessian2ObjectOutput4Hibernate(OutputStream outputStream) {
        super(outputStream, Hessian2SerializerFactory4Hibernate.SERIALIZER_FACTORY);
    }
}