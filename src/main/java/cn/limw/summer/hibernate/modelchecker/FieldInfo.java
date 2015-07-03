package cn.limw.summer.hibernate.modelchecker;

/**
 * @author li
 * @version 1 (2014年12月8日 下午12:13:24)
 * @since Java7
 */
public class FieldInfo {
    private String propertyName;

    private Boolean propertyNullability;

    public Boolean getPropertyNullability() {
        return propertyNullability;
    }

    public void setPropertyNullability(Boolean propertyNullability) {
        this.propertyNullability = propertyNullability;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}