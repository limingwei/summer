package summer.ioc.compiler;

import java.lang.reflect.Method;

/**
 * @author li
 * @version 1 (2015年10月11日 上午10:30:55)
 * @since Java7
 */
public class JavassistSummerCompilerUtil {
    public static String makeOverrideMethodSrc(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String src = "public " + method.getReturnType().getName() + " " + method.getName() + "(" + parameters(parameterTypes) + "){";
        src += "return super$" + method.getName() + "(" + arguments(parameterTypes) + ");";
        src += "}";
        return src;
    }

    // Object target = this;
    // Method method = null;
    // Object[] args = new Object[] { str, integer };
    //
    // AopFilter aopFilter = new AopFilter() {
    // public void doFilter(AopChain chain) {
    // System.err.println("before args=" + chain.getArgs());
    // chain.getArgs()[1] = "55555";
    // chain.doFilter();
    // System.err.println("after result=" + chain.getResult());
    // }
    // };
    //
    // List<AopFilter> filters = new ArrayList<AopFilter>(Arrays.asList(aopFilter));
    //
    // Invoker invoker = new Invoker() {
    // public Object invoke() {
    // return super$sayHi((String) getArgs()[0], (Integer) getArgs()[1]);
    // }
    // };
    //
    // return (String) new AopChain(target, method, args, filters, invoker).doFilter().getResult();

    public static String makeCallSuperMethodSrc(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String src = "public " + method.getReturnType().getName() + " super$" + method.getName() + "(" + parameters(parameterTypes) + "){";
        src += "return super." + method.getName() + "(" + arguments(parameterTypes) + ");";
        src += "}";
        return src;
    }

    /**
     * 引用实参列表
     */
    private static String arguments(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }
            src += " $param_" + i;
        }
        return src;
    }

    /**
     * 申明形参列表
     */
    private static String parameters(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }
            src += parameterTypes[i].getName() + " $param_" + i;
        }
        return src;
    }
}