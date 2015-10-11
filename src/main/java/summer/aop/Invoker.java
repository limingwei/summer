package summer.aop;

/**
 * @author li
 * @version 1 (2015年10月11日 上午9:27:02)
 * @since Java7
 */
public class Invoker {
    /**
     * 目标对象
     */
    private Object target;

    /**
     * 实参数组
     */
    private Object[] args;

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object[] getArgs() {
        return args;
    }

    public Invoker setArgs(Object[] args) {
        this.args = args;
        return this;
    }

    public Object invoke() {
        throw new RuntimeException();
    }
}