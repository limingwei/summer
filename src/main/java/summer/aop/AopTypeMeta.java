package summer.aop;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import summer.aop.util.AopUtil;
import summer.ioc.IocContext;
import summer.util.Assert;

/**
 * @author li
 * @version 1 (2015年10月14日 上午9:29:24)
 * @since Java7
 */
public class AopTypeMeta implements Serializable {
    private static final long serialVersionUID = 5393275910039016963L;

    private Map<String, Method> methodMap = new HashMap<String, Method>();

    private IocContext iocContext;

    private Object referenceTarget;

    private String referenceName;

    private Class<?> referenceType;

    public Map<String, Method> getMethodMap() {
        return methodMap;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public void setReferenceType(Class<?> referenceType) {
        this.referenceType = referenceType;
    }

    public void setIocContext(IocContext iocContext) {
        this.iocContext = iocContext;
    }

    public synchronized Object getReferenceTarget() {
        if (null == referenceTarget) {
            IocContext ioc = iocContext;
            Assert.noNull(ioc, "IocContext is null");
            referenceTarget = ioc.getBean(referenceType, referenceName);
        }
        return referenceTarget;
    }

    public AopFilter[] getAopFilters(String methodSignature) {
        return AopUtil.getAopFilters(getMethodMap().get(methodSignature), iocContext);
    }
}