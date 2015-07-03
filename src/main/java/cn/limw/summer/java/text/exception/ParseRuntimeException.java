package cn.limw.summer.java.text.exception;

/**
 * @author li
 */
public class ParseRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -7397569981485835891L;

    private int errorOffset;

    public ParseRuntimeException(String s, int errorOffset) {
        super(s);
        this.errorOffset = errorOffset;
    }

    public int getErrorOffset() {
        return errorOffset;
    }
}