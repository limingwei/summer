package li.mongo.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.unblocked.support.dao.Page;
import com.unblocked.support.web.spring.Mvcs;

/**
 * BaseTest
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年1月21日 下午3:51:55)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class BaseTest {
    protected Page page;

    @Before
    public void before() {
        page = new Page();
        page.setPageSize(3);
        Mvcs.init(new MockHttpServletRequest(), new MockHttpServletResponse());
    }

    public MockHttpServletRequest request() {
        return (MockHttpServletRequest) Mvcs.getRequest();
    }

    public BaseTest setParameters(Object... args) {
        Assert.isTrue(null != args && args.length % 2 == 0, "Count of items must be even !!!");
        for (int i = 0; i < args.length; i = i + 2) {
            request().setParameter(args[i] + "", args[i + 1] + "");
        }
        return this;
    }

    public MockHttpServletResponse response() {
        return (MockHttpServletResponse) Mvcs.getResponse();
    }
}