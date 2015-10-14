package summer.aop;

import java.lang.reflect.Method;
import java.util.List;

import summer.util.Assert;
import summer.util.ListUtil;
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
    private List<AopFilter> _filters;

    /**
     * AopFilter索引,指示当前执行到filter链中的第几个filter
     */
    private int _index = 0;

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

    public AopChain(String methodSignature, Object target, Object[] args, AopTypeMeta aopTypeMeta) {
        this(methodSignature, target, aopTypeMeta.getMethod(methodSignature), args, aopTypeMeta.getAopFilters(methodSignature));
    }

    public AopChain(String methodSignature, Object target, Method method, Object[] args, AopFilter[] filters) {
        this.methodSignature = methodSignature;
        this.target = target;
        this.method = method;
        this.args = args;
        this._filters = ListUtil.newList(filters);
    }

    /**
     * 执行AopChain,执行下一个AopFilter或者执行被代理方法
     */
    public AopChain doFilter() {
        if (null == _filters || _index >= _filters.size()) {// 如果没有AopFilter或者已经经过全部AopFilter
            invoke();// 执行目标方法
        } else {// 还有AopFilter
            _filters.get(_index++).doFilter(this);// 执行第index个AopFilter然后index++
        }
        return this;
    }

    /**
     * 执行目标方法
     */
    public AopChain invoke() {
        try {
            AopType aopTypeTarget = (AopType) getTarget();
            Assert.noNull(aopTypeTarget, "aopTypeTarget is null");

            setResult(aopTypeTarget.invoke(methodSignature, getArgs()));
        } catch (Throwable e) {
            throw new RuntimeException(e + " ", e);
        }
        return this;
    }

    public String toString() {
        return super.toString() + ", target=" + getTarget() + ", method=" + getMethod() + ", args=[" + StringUtil.join(getArgs(), ", ") + "], result=" + getResult() + ", filters=" + _filters + ", index=" + _index;
    }
}