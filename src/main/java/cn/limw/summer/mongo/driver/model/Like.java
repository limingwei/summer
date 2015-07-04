package cn.limw.summer.mongo.driver.model;

/**
 * 包装一个Like查询参数
 * 
 * @author li
 * @version 1 2014年3月11日下午4:02:31
 */
public class Like {
    /**
     * Like表达式,可能是Holder
     */
    private Object value;

    /**
     * 是否 NOT LIKE
     */
    private Boolean not;

    public Like(Boolean not, Object value) {
        this.not = not;
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public Boolean getNot() {
        return this.not;
    }
}