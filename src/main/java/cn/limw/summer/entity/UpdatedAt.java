package cn.limw.summer.entity;

import java.sql.Timestamp;

/**
 * @author li
 * @version 1 (2014年10月20日 下午5:27:32)
 * @since Java7
 */
public interface UpdatedAt {
    public Timestamp getUpdatedAt();

    public void setUpdatedAt(Timestamp updatedAt);
}