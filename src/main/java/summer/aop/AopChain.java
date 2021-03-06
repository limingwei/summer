package summer.aop;

import java.lang.reflect.Method;
import java.util.Map;

import summer.util.Assert;
import summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年10月10日 下午10:50:55)
 * @since Java7
 */
public class AopChain {
    private String methodSignature;

    /**
     * 目标对象
     */
    private Object target;

    /**
     * 目标 方法对象
     */
    private Method method;

    /**
     * 参数数组
     */
    private Object[] args;

    /**
     * 方法返回值
     */
    private Object result;

    /**
     * AopFilter列表
     */
    private AopFilter[] filters;

    /**
     * AopFilter索引,指示当前执行到filter链中的第几个filter
     */
    private int index = 0;

    /**
     * 返回被代理方法宿主对象
     */
    public Object getTarget() {
        return this.target;
    }

    /**
     * 返回被代理方法
     */
    public Method getMethod() {
        return this.method;
    }

    /**
     * 返回方法参数
     */
    public Object[] getArgs() {
        return this.args;
    }

    /**
     * 设置方法参数,在doChain之前才有效
     */
    public AopChain setArgs(Object[] args) {
        this.args = args;
        return this;
    }

    /**
     * 返回方法返回值,在方法执行后才有值
     */
    public Object getResult() {
        return this.result;
    }

    /**
     * 设置方法返回值,设置后不再doChain才有效
     */
    public AopChain setResult(Object result) {
        this.result = result;
        return this;
    }

    public AopChain(Object target, String methodSignature, Object[] args, AopTypeMeta aopTypeMeta) {
        Assert.noNull(aopTypeMeta, "aopTypeMeta is null");

        this.methodSignature = methodSignature;
        this.args = args;

        Map<String, Method> methodMap = aopTypeMeta.getMethodMap();
        this.method = methodMap.get(methodSignature);
        this.filters = aopTypeMeta.getAopFilters(methodSignature);

        if (null == aopTypeMeta.getBeanField()) {
            this.target = target;
        } else {
            this.target = aopTypeMeta.getTarget();
        }
    }

    /**
     * 执行AopChain,执行下一个AopFilter或者执行被代理方法
     */
    public AopChain doFilter() {
        if (null == filters || index >= filters.length) { // 如果没有AopFilter或者已经经过全部AopFilter
            invoke(); // 执行目标方法
        } else { // 还有AopFilter
            filters[index++].doFilter(this); // 执行第index个AopFilter然后index++
        }
        return this;
    }

    /**
     * 执行目标方法
     */
    public AopChain invoke() {
        try {
            if (getTarget() instanceof AopType) {
                AopType aopType = (AopType) getTarget();
                setResult(aopType.invoke(methodSignature, getArgs()));
            } else {
                throw new RuntimeException("target=" + getTarget() + ", methodSignature=" + methodSignature);
            }
        } catch (Throwable e) {
            if (e instanceof AbstractMethodError) {
                throw new RuntimeException("target.type=" + getTarget().getClass(), e);
            } else {
                throw (e instanceof RuntimeException) ? (RuntimeException) e : new RuntimeException(e);
            }
        }
        return this;
    }

    public String toString() {
        return super.toString() + ", target=" + getTarget() + ", method=" + getMethod() + ", args=[" + StringUtil.join(getArgs(), ", ") + "], result=" + getResult() + ", filters=" + filters + ", index=" + index;
    }
}