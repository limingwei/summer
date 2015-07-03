package cn.limw.summer.dao.summer;

import java.util.HashMap;
import java.util.Map;

import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2015年6月23日 下午3:18:53)
 * @since Java7
 */
@SuppressWarnings({ "rawtypes" })
public class QueryBuilderPool {
    public static final QueryBuilderPool INSTANCE = new QueryBuilderPool();

    private QueryBuilderProducer queryBuilderProducer = new QueryBuilderProducer();

    private Map<Class, QueryBuilder> cache = new HashMap<Class, QueryBuilder>();

    public QueryBuilder get(Class<?> type) {
        QueryBuilder queryBuilder = cache.get(type);
        if (null == queryBuilder) {
            Class<QueryBuilder> queryBuilderType = queryBuilderProducer.produce(type);
            cache.put(type, queryBuilder = Mirrors.newInstance(queryBuilderType));
        }
        return queryBuilder;
    }
}