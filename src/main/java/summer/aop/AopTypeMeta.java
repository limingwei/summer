package summer.aop;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import summer.aop.util.AopUtil;
import summer.ioc.BeanField;
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

    private Map<String, AopFilter[]> methodAopFiltersMap = new HashMap<String, AopFilter[]>();

    private IocContext iocContext;

    private BeanField beanField;

    private Object target;

    /**
     * Aop对象target不为空 属性代理对象第一次target为空
     */
    public Object getTarget() {
        return null != target ? target : getBeanFieldReferenceInjectDelegateTarget(iocContext, beanField);
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    private synchronized Object getBeanFieldReferenceInjectDelegateTarget(IocContext iocContext, BeanField beanField) {
        if (null == target) {
            Assert.noNull(beanField, "target 和 beanField 均为空");
            Object targetBean = iocContext.getBean(beanField.getType(), beanField.getValue());
            System.err.println("targetBean.type=" + targetBean.getClass());
//            if (Object.class.equals(targetBean.getClass().getSuperclass())) {
//                throw new RuntimeException("getBeanFieldReferenceInjectDelegateTarget 不是一个类代理对象, " + targetBean.getClass());
//            } else {
                target = targetBean;
//            }
        }
        return target;
    }

    public Map<String, Method> getMethodMap() {
        return methodMap;
    }

    public void setBeanField(BeanField beanField) {
        this.beanField = beanField;
    }

    public BeanField getBeanField() {
        return beanField;
    }

    public void setIocContext(IocContext iocContext) {
        this.iocContext = iocContext;
    }

    public AopFilter[] getAopFilters(String methodSignature) {
        if (null == beanField) {
            AopFilter[] aopFilters = methodAopFiltersMap.get(methodSignature);
            if (null == aopFilters) {
                Method method = getMethodMap().get(methodSignature);
                methodAopFiltersMap.put(methodSignature, aopFilters = AopUtil.getAopFilters(method, iocContext));
            }
            return aopFilters;
        } else {
            return new AopFilter[0];
        }
    }
}