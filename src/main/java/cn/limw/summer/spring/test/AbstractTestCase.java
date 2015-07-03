package cn.limw.summer.spring.test;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cn.limw.summer.spring.test.web.servlet.Handlers;
import cn.limw.summer.spring.test.web.servlet.ResultActionsWrapper;
import cn.limw.summer.spring.test.web.servlet.request.Builders;
import cn.limw.summer.spring.test.web.servlet.result.Matchers;
import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年10月22日 下午5:07:08)
 * @since Java7
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:dev/dev-spring.xml")
//@Transactional
//@TransactionConfiguration(defaultRollback = true)
//@ActiveProfiles("dev")
//@WebAppConfiguration(value = "src/main/webapp")
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
public class AbstractTestCase implements Handlers, Matchers {
    private MockMvc mockMvc;

    public MockMvc getMockMvc() {
        if (null == mockMvc) {
            if (null == getWebApplicationContext()) {
                throw new RuntimeException("getWebApplicationContext() returns null");
            } else {
                setMockMvc(MockMvcBuilders.webAppContextSetup(getWebApplicationContext()).build());
            }
        }
        return Asserts.noNull(mockMvc);
    }

    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public WebApplicationContext getWebApplicationContext() {
        return null;
    }

    public Builders builder() {
        return new Builders(getMockMvc());
    }

    /**
     * 执行 POST Mock 请求
     * @param url 请求地址
     * @param paramNamesAndValues 参数列表
     */
    protected ResultActionsWrapper doPost(String url, String... paramNamesAndValues) {
        return builder().post(url).params(paramNamesAndValues).perform().andDo(info);
    }

    /**
     * 执行 GET Mock 请求
     * @param url 请求地址
     * @param paramNamesAndValues 参数列表
     */
    protected ResultActionsWrapper doGet(String url, String... paramNamesAndValues) {
        return builder().get(url).params(paramNamesAndValues).perform().andDo(info);
    }

    /**
     * 执行 DELETE 请求
     * @param url 请求地址
     * @param paramNamesAndValues 参数列表
     */
    protected ResultActionsWrapper doDelete(String url, String... paramNamesAndValues) {
        return builder().delete(url).params(paramNamesAndValues).perform().andDo(info);
    }
}