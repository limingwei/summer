package li.mongo.driver.model;

/**
 * SQL参数Holder
 * 
 * @author li
 * @version 1 2014年3月12日上午9:38:40
 */
public class Holder {
    /**
     * 参数索引
     */
    private Integer index;

    public Holder(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return this.index;
    }

    public String toString() {
        return "$(" + index + ")";
    }

    public void setSign(char sign) {}
}