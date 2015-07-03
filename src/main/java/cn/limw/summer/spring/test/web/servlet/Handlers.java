package cn.limw.summer.spring.test.web.servlet;

import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import cn.limw.summer.spring.test.web.servlet.result.handler.ConsolePrintingRequestInfoResultHandler;

/**
 * @author li
 * @version 1 (2014年11月19日 上午9:47:39)
 * @since Java7
 */
public interface Handlers {
    public ResultHandler print = MockMvcResultHandlers.print();

    public ResultHandler info = ConsolePrintingRequestInfoResultHandler.INSTANCE;
}