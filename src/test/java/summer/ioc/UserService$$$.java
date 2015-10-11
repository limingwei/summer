package summer.ioc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.dubbo.common.json.JSON;

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
        Method method = null;
        Object[] args = new Object[] { str, integer };

        AopFilter aopFilter = new AopFilter() {
            public void doFilter(AopChain chain) {
                System.err.println("before args=" + chain.getArgs());
                chain.getArgs()[1] = "55555";
                chain.doFilter();
                System.err.println("after result=" + chain.getResult());
            }
        };

        List<AopFilter> filters = new ArrayList<AopFilter>(Arrays.asList(aopFilter));

        Invoker invoker = new Invoker() {
            public Object invoke() {
                Object[] args = getArgs();
                return super$sayHi((String) args[0], (Integer) args[1]);
            }
        };

        return (String) new AopChain(target, method, args, filters, invoker).doFilter().getResult();
    }

    public String super$sayHi(String str, Integer integer) {
        return super.sayHi(str, integer);
    }

    public static void main(String[] args) {
        new UserService$$$().sayHi("aaaaaaaaaaaa", 111);
    }
}