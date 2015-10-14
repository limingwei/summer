package summer.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import summer.aop.util.AopUtil;
import summer.ioc.IocContext;
import summer.mvc.annotation.At;
import summer.util.Assert;

/**
 * @author li
 * @version 1 (2015年10月14日 上午9:29:24)
 * @since Java7
 */
public class AopTypeMeta {
    private IocContext iocContext;

    private Object referenceTarget;

    private String referenceName;

    private Class<?> referenceType;

    private Map<String, Method> methodMap = new HashMap<String, Method>();

    public Method getMethod(String methodSignature) {
        return methodMap.get(methodSignature);
    }

    public Map<String, Method> getMethodMap() {
        return methodMap;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public Class<?> getReferenceType() {
        return referenceType;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public void setReferenceType(Class<?> referenceType) {
        this.referenceType = referenceType;
    }

    public void setReferenceTarget(Object referenceTarget) {
        this.referenceTarget = referenceTarget;
    }

    public IocContext getIocContext() {
        return iocContext;
    }

    public void setIocContext(IocContext iocContext) {
        this.iocContext = iocContext;
    }

    public synchronized Object getReferenceTarget() {
        if (null == referenceTarget) {
            IocContext ioc = getIocContext();
            Assert.noNull(ioc, "IocContext is null");
            referenceTarget = ioc.getBean(getReferenceType(), getReferenceName());
        }
        return referenceTarget;
    }

    public AopFilter[] getAopFilters(String methodSignature) {
        Method method = getMethod(methodSignature);
        List<AopFilter> aopFilters = new ArrayList<AopFilter>();
        if (null != method.getAnnotation(At.class)) { // 类型
            aopFilters.add(AopUtil.getParameterAdapterAopFilter(getIocContext()));
        }
        Aop aop = method.getAnnotation(Aop.class);
        if (null != aop) {
            for (String aopBeanName : aop.value()) { // 名称
                aopFilters.add(AopUtil.getAopFilter(getIocContext(), aopBeanName));
            }
        }
        Transaction transaction = method.getAnnotation(Transaction.class);
        if (null != transaction) { // 名称
            aopFilters.add(AopUtil.getTransactionAopFilter(getIocContext()));
        }
        if (null != method.getAnnotation(At.class)) { // 类型
            aopFilters.add(AopUtil.getViewProcessorAopFilter(getIocContext()));
        }
        return aopFilters.toArray(new AopFilter[aopFilters.size()]);
    }
}