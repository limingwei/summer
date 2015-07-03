package cn.limw.summer.spring.test.web.servlet.request;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年11月19日 上午9:36:11)
 * @since Java7
 */
public class Builders {
    private MockMvc mockMvc;

    public Builders(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public MockRequestBuilder get(String urlTemplate, Object... urlVariables) {
        return new MockRequestBuilder(getMockMvc(), MockMvcRequestBuilders.get(urlTemplate, urlVariables));
    }

    public MockRequestBuilder post(String urlTemplate, Object... urlVariables) {
        return new MockRequestBuilder(getMockMvc(), MockMvcRequestBuilders.post(urlTemplate, urlVariables));
    }

    public MockRequestBuilder delete(String urlTemplate, Object... urlVariables) {
        return new MockRequestBuilder(getMockMvc(), MockMvcRequestBuilders.delete(urlTemplate, urlVariables));
    }

    public MockRequestBuilder put(String urlTemplate, Object... urlVariables) {
        return new MockRequestBuilder(getMockMvc(), MockMvcRequestBuilders.put(urlTemplate, urlVariables));
    }

    public MockMvc getMockMvc() {
        return Asserts.noNull(mockMvc);
    }
}