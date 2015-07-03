package cn.limw.summer.spring.test.web.servlet.result;

import org.junit.Assert;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

/**
 * @author li
 * @version 1 (2014年10月24日 下午6:11:32)
 * @since Java7
 */
public class RequestResultMatchers extends AbstractRequestResultMatchers {
    public static final RequestResultMatchers INSTANCE = new RequestResultMatchers();

    public <T> ResultMatcher attribute(final String name) {
        return new ResultMatcher() {
            public void match(MvcResult mvcResult) {
                Assert.assertNotNull("request attribute " + name + " must not be null", mvcResult.getRequest().getAttribute(name));
            }
        };
    }

    public ResultMatcher forward(final String forwardedUrl) {
        return new ResultMatcher() {
            public void match(MvcResult mvcResult) {
                Assert.assertEquals(200, mvcResult.getResponse().getStatus());
                Assert.assertEquals(forwardedUrl, mvcResult.getResponse().getForwardedUrl());
            }
        };
    }
}