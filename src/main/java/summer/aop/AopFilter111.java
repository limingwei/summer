package summer.aop;

/**
 * @author li
 * @version 1 (2015年10月11日 下午12:45:46)
 * @since Java7
 */
public class AopFilter111 implements AopFilter {
    public void doFilter(AopChain chain) {
        System.out.println("AopFilter111.doFilter()");
        System.err.println(chain.getTarget());
        System.err.println(chain.getMethod());
        System.err.println(chain.getArgs());
//        chain.setArgs(new Object[] { "def", 222, true });
        chain.doFilter();
//        chain.setResult("abc");
        System.err.println(chain.getResult());
    }
}