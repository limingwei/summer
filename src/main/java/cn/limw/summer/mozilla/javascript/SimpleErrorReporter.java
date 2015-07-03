package cn.limw.summer.mozilla.javascript;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年6月25日 下午1:39:59)
 * @since Java7
 */
public class SimpleErrorReporter implements ErrorReporter {
    public static final SimpleErrorReporter INSTANCE = new SimpleErrorReporter();

    private static final Logger log = Logs.slf4j();

    public void error(String arg0, String arg1, int arg2, String arg3, int arg4) {
        //        log.error("error arg0=" + arg0 + ", arg1=" + arg1 + ", arg2=" + arg2 + ", arg3=" + arg3 + ", arg4=" + arg4);
    }

    public EvaluatorException runtimeError(String msg, String sourceURI, int baseLineno, String arg3, int arg4) {
        log.error("runtimeError arg0=" + msg + ", arg1=" + sourceURI + ", baseLineno=" + baseLineno + ", arg3=" + arg3 + ", arg4=" + arg4);
        return new EvaluatorException("runtimeError msg=" + msg + ", sourceURI=" + sourceURI + ", baseLineno=" + baseLineno + ", arg3=" + arg3 + ", arg4=" + arg4);
    }

    public void warning(String arg0, String arg1, int arg2, String arg3, int arg4) {
        //        log.warn("warning arg0=" + arg0 + ", arg1=" + arg1 + ", arg2=" + arg2 + ", arg3=" + arg3 + ", arg4=" + arg4);
    }
}