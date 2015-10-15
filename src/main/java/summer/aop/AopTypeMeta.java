package summer.aop;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import summer.aop.util.AopUtil;
import summer.ioc.BeanField;
import summer.ioc.IocContext;

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

    public Map<String, Method> getMethodMap() {
        return methodMap;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setBeanField(BeanField beanField) {
        this.beanField = beanField;
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

    public Object getInvokeTarget() {// 第一次target为空表示是属性代理对象
        return null != target ? target : getBeanFieldReferenceInjectDelegateTarget(iocContext, beanField);
    }

    private synchronized Object getBeanFieldReferenceInjectDelegateTarget(IocContext iocContext, BeanField beanField) {
        if (null == target) { // 第一次target为空表示是属性代理对象
            target = iocContext.getBean(beanField.getType(), beanField.getValue());
        }
        return target;
    }
}