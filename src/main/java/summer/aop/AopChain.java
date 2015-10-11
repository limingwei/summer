package summer.aop;

import java.lang.reflect.Method;
import java.util.List;

import summer.util.ListUtil;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月10日 下午10:50:55)
 * @since Java7
 */
public class AopChain {
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
    private List<AopFilter> filters;

    /**
     * AopFilter索引,指示当前执行到filter链中的第几个filter
     */
    private int index = 0;

    private Invoker invoker;

    public Invoker getInvoker() {
        return invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

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

    /**
     * 初始化一个AopChain
     */
    public AopChain(Object target, Method method, Object[] args, List<AopFilter> filters, Invoker invoker) {
        this.target = target;
        this.method = method;
        this.args = args;
        this.filters = filters;
        this.invoker = invoker;
    }

    public AopChain(Object target, Method method, Object[] args, AopFilter[] filters, Invoker invoker) {
        this(target, method, args, ListUtil.newList(filters), invoker);
    }

    /**
     * 执行AopChain,执行下一个AopFilter或者执行被代理方法
     */
    public AopChain doFilter() {
        if (null == filters || index >= filters.size()) {// 如果没有AopFilter或者已经经过全部AopFilter
            invoke();// 执行目标方法
        } else {// 还有AopFilter
            filters.get(index++).doFilter(this);// 执行第index个AopFilter然后index++
        }
        return this;
    }

    /**
     * 执行目标方法
     */
    public AopChain invoke() {
        try {
            if (null == getInvoker()) {
                setResult(Reflect.invokeMethod(getTarget(), getMethod(), getArgs()));
            } else {
                setResult(getInvoker().setArgs(getArgs()).invoke());
            }
        } catch (Throwable e) {
            throw new RuntimeException(e + " ", e);
        }
        return this;
    }
}