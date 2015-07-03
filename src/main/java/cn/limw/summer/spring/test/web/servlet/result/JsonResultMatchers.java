package cn.limw.summer.spring.test.web.servlet.result;

import java.util.Map;

import org.junit.Assert;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import cn.limw.summer.util.Jsons;

/**
 * @author li
 * @version 1 (2014年11月10日 上午11:20:15)
 * @since Java7
 */
public class JsonResultMatchers {
    public static final JsonResultMatchers INSTANCE = new JsonResultMatchers();

    public ResultMatcher attribute(final String name) {
        return new ResultMatcher() {
            public void match(MvcResult mvcResult) {
                matchAttribute(mvcResult, name);
            }
        };
    }

    protected void matchAttribute(MvcResult mvcResult, String name) {
        try {
            String response = mvcResult.getResponse().getContentAsString();
            Map map = Jsons.toMap(response);
            Assert.assertNotNull("response must has json attribute " + name + ", " + response, map.get(name));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}