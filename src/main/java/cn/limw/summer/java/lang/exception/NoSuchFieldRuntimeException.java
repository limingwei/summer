package cn.limw.summer.java.lang.exception;

/**
 * @author li
 * @version 1 (2014年12月24日 上午9:38:17)
 * @since Java7
 */
public class NoSuchFieldRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -8631792316789902962L;

    private String targetType;

    private String fieldName;

    public NoSuchFieldRuntimeException(String targetType, String fieldName, RuntimeException e) {
        super(e);
        this.targetType = targetType;
        this.fieldName = fieldName;
    }

    public String getTargetType() {
        return targetType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return "type " + targetType + " not has field " + fieldName;
    }
}