package cn.limw.summer.dao.summer;

import java.sql.ResultSet;

/**
 * @author li
 * @version 1 (2015年6月23日 下午3:10:30)
 * @since Java7
 */
public interface EntityMaker<T> {
    public T toEntity(ResultSet resultSet);
}