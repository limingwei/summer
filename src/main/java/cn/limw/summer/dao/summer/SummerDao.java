package cn.limw.summer.dao.summer;

import java.sql.ResultSet;

/**
 * @author li
 * @version 1 (2015年6月23日 下午3:06:15)
 * @since Java7
 */
public class SummerDao extends SummerDaoSupport {
    public <T> T findById(Class<T> type, Integer id) {
        QueryBuilder queryBuilder = getQueryBuilder(type);
        String sql = queryBuilder.findById();

        QueryRunner queryRunner = getQueryRunner();
        ResultSet resultSet = queryRunner.executeQuery(sql);

        EntityMaker<T> entityMaker = getEntityMaker(type);
        return entityMaker.toEntity(resultSet);
    }

    public <T> T findBySql(Class<T> type, String sql) {
        QueryRunner queryRunner = getQueryRunner();
        ResultSet resultSet = queryRunner.executeQuery(sql);

        EntityMaker<T> entityMaker = getEntityMaker(type);
        T entity = entityMaker.toEntity(resultSet);
        ((DefaultEntityLazyLoader) ((EntityLazyLoaderHolder) entity).getEntityLazyLoader()).setLoaded(true);
        return entity;
    }
}