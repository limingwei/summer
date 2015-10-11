package summer.aop;

/**
 * @author li
 * @version 1 (2015年10月11日 上午9:27:02)
 * @since Java7
 */
public abstract class Invoker {
    /**
     * 实参数组
     */
    private Object[] args;

    public Object[] getArgs() {
        return args;
    }

    public Invoker setArgs(Object[] args) {
        this.args = args;
        return this;
    }

    public abstract Object invoke();
}