package summer.ioc;

/**
 * @author li
 * @version 1 (2015年10月10日 下午2:21:20)
 * @since Java7
 */
public class BeanField {
    public static final String INJECT_TYPE_VALUE = "inject_type_value";

    public static final String INJECT_TYPE_REFERENCE = "inject_type_reference";

    private String injectType;

    private String name;

    private String value;

    public BeanField() {}

    public BeanField(String injectType, String name, String value) {
        this.injectType = injectType;
        this.name = name;
        this.value = value;
    }

    public String getInjectType() {
        return injectType;
    }

    public void setInjectType(String injectType) {
        this.injectType = injectType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}