package cn.limw.summer.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author li ( limingwei@mail.com )
 * @version 1 ( 2014年5月16日 下午2:21:38 )
 */
public class Errors {
    public static RuntimeException notImplemented() {
        return new RuntimeException("method not implemented");
    }

    public static RuntimeException wrap(String message, Throwable cause) {
        return new RuntimeException(message, cause);
    }

    public static RuntimeException wrap(Throwable cause) {
        return new RuntimeException(cause);
    }

    public static RuntimeException wrap(String message) {
        return new RuntimeException(message);
    }

    public static String stackTrace(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static Throwable newThrowableWithStackTraceOfType(Class type, String message) {
        Throwable throwable = new Throwable(message);
        StackTraceElement[] stackTrace = new StackTraceElement[] { new StackTraceElement(type.getName(), "", type.getSimpleName() + ".java", 1) };
        throwable.setStackTrace(stackTrace);
        return throwable;
    }
}