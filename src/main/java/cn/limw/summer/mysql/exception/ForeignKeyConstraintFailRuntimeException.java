package cn.limw.summer.mysql.exception;

/**
 * @author li
 * @version 1 (2014年12月24日 上午11:29:55)
 * @since Java7
 */
public class ForeignKeyConstraintFailRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -103828712656325662L;

    public ForeignKeyConstraintFailRuntimeException(String message, Exception e) {
        super(message, e);
    }
}