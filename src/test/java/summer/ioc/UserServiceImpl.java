package summer.ioc;

/**
 * @author li
 * @version 1 (2015年10月11日 上午9:30:48)
 * @since Java7
 */
public class UserServiceImpl implements IUserService {
    public String sayHi(String str, Integer integer) {
        System.err.println("str=" + str + ", integer=" + integer);
        return "return str=" + str + ", integer=" + integer;
    }
}