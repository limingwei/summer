//package summerdao;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//import org.junit.Test;
//
///**
// * @author li
// * @version 1 (2015年6月23日 下午3:01:57)
// * @since Java7
// */
//public class SummerDaoTest extends BaseTest {
//    @Resource
//    DataSource dataSource;
//
//    @Test
//    public void findBySql() {
//        SummerDao summerDao = new SummerDao();
//        summerDao.setDataSource(dataSource);
//        User user = summerDao.findBySql(User.class, "select * from user limit 1");
//        System.err.println(user.getProvider());
//    }
//}