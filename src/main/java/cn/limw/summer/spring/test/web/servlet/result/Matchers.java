package cn.limw.summer.spring.test.web.servlet.result;

import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.result.ViewResultMatchers;

/**
 * @author li
 * @version 1 (2014年11月19日 上午9:46:32)
 * @since Java7
 */
public interface Matchers {
    public RequestResultMatchers request = RequestResultMatchers.INSTANCE;

    public ResponseResultMatchers response = ResponseResultMatchers.INSTANCE;

    public StatusResultMatchers status = response.status();

    public ContentResultMatchers content = response.content();

    public ViewResultMatchers view = response.view();
}