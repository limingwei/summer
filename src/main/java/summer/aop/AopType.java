package summer.aop;

/**
 * @author li
 * @version 1 (2015年10月14日 上午9:28:17)
 * @since Java7
 */
public interface AopType {
    public AopTypeMeta getAopTypeMeta();

    /**
     * 访问指定签名方法 super.invoke
     */
    public Object invoke(String methodSignature, Object[] args);

    /**
     * 访问指定签名方法 this.call
     */
    public Object call(String methodSignature, Object[] args);
}