package cn.limw.summer.spring.test.web.servlet.request;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.Assert;

import cn.limw.summer.spring.test.web.servlet.ResultActionsWrapper;
import cn.limw.summer.spring.test.web.servlet.request.util.MockHttpServletRequestBuilderUtil;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2014年11月10日 上午9:42:18)
 * @since Java7
 * @see org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
 */
public class MockRequestBuilder implements RequestBuilder {
    private MockMvc mockMvc;

    private MockHttpServletRequestBuilder mockHttpServletRequestBuilder;

    public MockRequestBuilder(MockMvc mockMvc, MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        this.mockMvc = mockMvc;
        this.mockHttpServletRequestBuilder = mockHttpServletRequestBuilder;
    }

    public MockHttpServletRequest buildRequest(ServletContext servletContext) {
        return MockHttpServletRequestBuilderUtil.buildRequest(mockHttpServletRequestBuilder, servletContext);
    }

    /**
     * 执行
     */
    public ResultActionsWrapper perform() {
        try {
            ResultActions resultActions = mockMvc.perform((RequestBuilder) this);
            return new ResultActionsWrapper(resultActions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MockRequestBuilder param(String name, String... values) {
        Assert.hasLength(name, "'param.name' must not be empty");
        mockHttpServletRequestBuilder.param(name, values);
        return this;
    }

    public MockRequestBuilder params(String... paramNamesAndValues) {
        Asserts.isTrue(paramNamesAndValues.length % 2 == 0, "参数键值对应当为偶数个");
        for (int i = 0; i < paramNamesAndValues.length; i = i + 2) {
            param(paramNamesAndValues[i], paramNamesAndValues[i + 1]);
        }
        return this;
    }

    public MockRequestBuilder params(Map parameters) {
        Set<Entry> entrySet = parameters.entrySet();
        for (Entry entry : entrySet) {
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                param(key, (String) value);
            } else if (value instanceof String[]) {
                String[] array = (String[]) value;
                for (String each : array) {
                    param(key, each);
                }
            } else {
                throw new IllegalArgumentException("参数类型错误 " + Mirrors.getType(value));
            }
        }
        return this;
    }

    public MockRequestBuilder headers(String... paramNamesAndValues) {
        for (int i = 0; i < paramNamesAndValues.length; i = i + 2) {
            header(paramNamesAndValues[i], paramNamesAndValues[i + 1]);
        }
        return this;
    }

    public MockRequestBuilder header(String name, Object... values) {
        mockHttpServletRequestBuilder.header(name, values);
        return this;
    }

    public MockRequestBuilder content(byte[] content) {
        mockHttpServletRequestBuilder.content(content);
        return this;
    }

    public MockRequestBuilder content(String content) {
        mockHttpServletRequestBuilder.content(content.replace('\'', '\"'));
        return this;
    }
}