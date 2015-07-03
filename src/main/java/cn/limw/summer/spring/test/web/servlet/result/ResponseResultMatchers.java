package cn.limw.summer.spring.test.web.servlet.result;

import java.io.UnsupportedEncodingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.result.ViewResultMatchers;

/**
 * @author li
 * @version 1 (2014年10月27日 上午11:21:51)
 * @since Java7
 */
public class ResponseResultMatchers {
    public static final ResponseResultMatchers INSTANCE = new ResponseResultMatchers();

    public ContentResultMatchers content() {
        return ContentResultMatchers.INSTANCE;
    }

    public StatusResultMatchers status() {
        return MockMvcResultMatchers.status();
    }

    public ViewResultMatchers view() {
        return MockMvcResultMatchers.view();
    }

    public JsonResultMatchers json() {
        return JsonResultMatchers.INSTANCE;
    }

    public ResultMatcher title(final String title) {
        return new ResultMatcher() {
            public void match(MvcResult mvcResult) {
                assertTitle(title, mvcResult);
            }
        };
    }

    public ResultMatcher redirect(final String redirectedUrl) {
        return new ResultMatcher() {
            public void match(MvcResult mvcResult) {
                assertRedirect(redirectedUrl, mvcResult);
            }
        };
    }

    public ResultMatcher contains(final String text) {
        return content().contains(text);
    }

    /**
     * 断言返回页面的Title等于指定字符串
     */
    private static void assertTitle(String title, MvcResult mvcResult) {
        try {
            String contentAsString = mvcResult.getResponse().getContentAsString();
            Document document = Jsoup.parse(contentAsString);
            String docTitle = document.title();
            Assert.assertEquals(title, docTitle);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 断言客户端跳转地址
     */
    private static void assertRedirect(String redirectedUrl, MvcResult mvcResult) {
        Assert.assertEquals(302, mvcResult.getResponse().getStatus());
        Assert.assertEquals(redirectedUrl, mvcResult.getResponse().getRedirectedUrl());
    }
}