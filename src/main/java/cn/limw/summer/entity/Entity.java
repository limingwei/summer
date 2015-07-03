package cn.limw.summer.entity;

import java.io.Serializable;

/**
 * @author li
 * @version 1 (2014年12月9日 下午6:18:13)
 * @since Java7
 */
public class Entity implements Serializable {
    private static final long serialVersionUID = -5354584977693827076L;

    public String toString() {
        String string = super.toString();
        if (this instanceof Id) {
            string += ", id=" + ((Id) this).getId();
        }
        if (this instanceof CreatedAt) {
            string += ", createdAt=" + ((CreatedAt) this).getCreatedAt();
        }
        if (this instanceof UpdatedAt) {
            string += ", updatedAt=" + ((UpdatedAt) this).getUpdatedAt();
        }
        return string;
    }
}