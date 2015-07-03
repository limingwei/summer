package cn.limw.summer.dao.summer;

import javax.sql.DataSource;

/**
 * @author li
 * @version 1 (2015年6月23日 下午3:08:52)
 * @since Java7
 */
public class SummerDaoSupport {
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public QueryBuilder getQueryBuilder(Class<?> type) {
        return QueryBuilderPool.INSTANCE.get(type);
    }

    public <T> EntityMaker<T> getEntityMaker(Class<T> type) {
        return EntityMakerPool.INSTANCE.get(type);
    }

    public QueryRunner getQueryRunner() {
        DefaultQueryRunner defaultQueryRunner = new DefaultQueryRunner();
        defaultQueryRunner.setDataSource(getDataSource());
        return defaultQueryRunner;
    }
}