package summer.mvc.mock;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import summer.log.Logger;
import summer.util.Log;

/**
 * @author li
 * @version 1 (2015年10月12日 下午10:57:30)
 * @since Java7
 */
public class MockRequestDispatcher implements RequestDispatcher {
    private static final Logger log = Log.slf4j();

    private String path;

    public MockRequestDispatcher(String path) {
        this.path = path;
    }

    public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        log.debug("forward to : " + path);
    }

    public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        log.debug("include : " + path);
    }
}