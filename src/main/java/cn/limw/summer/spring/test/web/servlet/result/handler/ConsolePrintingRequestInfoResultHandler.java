package cn.limw.summer.spring.test.web.servlet.result.handler;

import java.io.PrintStream;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.result.PrintingResultHandler;
import org.springframework.util.CollectionUtils;

import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年11月10日 上午10:36:38)
 * @since Java7
 */
public class ConsolePrintingRequestInfoResultHandler extends PrintingResultHandler {
    public static final ConsolePrintingRequestInfoResultHandler INSTANCE = new ConsolePrintingRequestInfoResultHandler();

    public ConsolePrintingRequestInfoResultHandler() {
        super(new ResultValuePrinter() {
            PrintStream printStream = System.out;

            public void printHeading(String heading) {
                printStream.println("ConsolePrintingRequestInfoResultHandler(info)");
                printStream.println(String.format("%20s:", heading));
            }

            public void printValue(String label, Object value) {
                if (value != null && value.getClass().isArray()) {
                    value = CollectionUtils.arrayToList(value);
                }
                printStream.println(String.format("%20s = %s", label, value));
            }
        });
    }

    protected void printResponse(MockHttpServletResponse response) throws Exception {
        getPrinter().printValue("Status", response.getStatus());
        getPrinter().printValue("Error message", response.getErrorMessage());
        getPrinter().printValue("Headers", getResponseHeaders(response));
        getPrinter().printValue("Content type", response.getContentType());
        printBody(response);
        getPrinter().printValue("Forwarded URL", response.getForwardedUrl());
        getPrinter().printValue("Redirected URL", response.getRedirectedUrl());
        getPrinter().printValue("Cookies", response.getCookies());
    }

    /**
     * 过长的html响应不完全打印
     */
    private void printBody(MockHttpServletResponse response) throws Exception {
        String contentAsString = response.getContentAsString();
        int len = contentAsString.length();
        if (StringUtil.containsIgnoreCase(response.getContentType(), "text/html") && len > 200) {//长度超过200的html响应
            getPrinter().printValue("Body", StringUtil.substring(contentAsString, 0, 100) + "\n ... <<<" + contentAsString.length() + " chars>>> ... \n" + StringUtil.substring(contentAsString, 0, -100));
        } else if (len > 500) {//长度超过500的所有响应
            getPrinter().printValue("Body", StringUtil.substring(contentAsString, 0, 250) + "\n ... <<<" + contentAsString.length() + " chars>>> ... \n" + StringUtil.substring(contentAsString, 0, -250));
        } else {
            getPrinter().printValue("Body", contentAsString);
        }
    }
}