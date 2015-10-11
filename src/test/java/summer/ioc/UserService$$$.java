package summer.ioc;

import java.lang.reflect.Method;
import java.util.List;

import summer.aop.AopChain;
import summer.aop.AopFilter;
import summer.aop.Invoker;

/**
 * @author li
 * @version 1 (2015年10月11日 上午9:29:36)
 * @since Java7
 */
public class UserService$$$ extends UserServiceImpl {
    public String sayHi(String str, Integer integer) {
        Object target = this;
        Object[] args = new Object[] { str, integer };

        Method method = getMethod();
        List<AopFilter> filters = getAopFilters(method);

        Invoker invoker = new Invoker() {
            public Object invoke() {
                Object[] args = getArgs();
                return super$sayHi((String) args[0], (Integer) args[1]);
            }
        };

        return (String) new AopChain(target, method, args, filters, invoker).doFilter().getResult();
    }

    private Method getMethod() {
        return null;
    }

    private List<AopFilter> getAopFilters(Method method) {
        return null;
    }

    public String super$sayHi(String str, Integer integer) {
        return super.sayHi(str, integer);
    }

    public static void main(String[] args) {
        new UserService$$$().sayHi("aaaaaaaaaaaa", 111);
    }
}