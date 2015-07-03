package cn.limw.summer.spring.test.web.servlet.result;

import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

/**
 * @author li
 * @version 1 (2014年10月27日 上午11:49:51)
 * @since Java7
 */
public class ContentResultMatchers extends AbstractContentResultMatchers {
    public static final ContentResultMatchers INSTANCE = new ContentResultMatchers();

    public ResultMatcher contains(final String text) {
        return new ResultMatcher() {
            public void match(MvcResult mvcResult) {
                assertContains(text, mvcResult);
            }
        };
    }

    /**
     * 断言返回页面内容包含指定字符串
     */
    private static void assertContains(String text, MvcResult mvcResult) {
        try {
            String contentAsString = mvcResult.getResponse().getContentAsString();
            Assert.assertTrue("[" + contentAsString + "] not contains [" + text + "]", contentAsString.contains(text));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}