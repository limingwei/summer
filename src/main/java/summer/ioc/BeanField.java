package summer.ioc;

/**
 * @author li
 * @version 1 (2015年10月10日 下午2:21:20)
 * @since Java7
 */
public class BeanField {
    public static final String TYPE_PROPERTY_VALUE = "property_value";

    public static final String TYPE_REFERENCE = "reference";

    private String type;

    private String name;

    private String value;

    public BeanField(String type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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