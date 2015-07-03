package cn.limw.summer.spring.test.web.servlet;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;

import cn.limw.summer.util.Asserts;

/**
 * 包装一个 ResultActions 不抛异常
 * @author li
 * @version 1 (2014年10月27日 下午1:40:23)
 * @since Java7
 */
public class ResultActionsWrapper implements org.springframework.test.web.servlet.ResultActions {
    private ResultActions resultActions;

    public ResultActionsWrapper(ResultActions resultActions) {
        this.resultActions = resultActions;
    }

    public ResultActions getResultActions() {
        return Asserts.noNull(resultActions);
    }

    public ResultActionsWrapper andExpect(ResultMatcher matcher) {
        try {
            getResultActions().andExpect(matcher);
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultActionsWrapper andDo(ResultHandler handler) {
        try {
            getResultActions().andDo(handler);
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MvcResult andReturn() {
        return getResultActions().andReturn();
    }
}