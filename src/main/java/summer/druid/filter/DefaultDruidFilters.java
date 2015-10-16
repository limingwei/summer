package summer.druid.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import summer.ioc.FactoryBean;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.MergeStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;

/**
 * @author li
 * @version 1 (2015年10月16日 上午11:38:55)
 * @since Java7
 */
public class DefaultDruidFilters implements FactoryBean<List<Filter>> {
    public List<Filter> getObject() {
        return new ArrayList<Filter>(Arrays.asList(
                newMergeStatFilter(), //
                newWallFilter(), //
                newSlf4jLogFilter()));
    }

    private MergeStatFilter newMergeStatFilter() {
        MergeStatFilter mergeStatFilter = new MergeStatFilter();
        mergeStatFilter.setSlowSqlMillis(3 * 1000);
        mergeStatFilter.setLogSlowSql(true);
        return mergeStatFilter;
    }

    private Slf4jLogFilter newSlf4jLogFilter() {
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        return slf4jLogFilter;
    }

    private WallFilter newWallFilter() {
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(newWallConfig());
        return wallFilter;
    }

    private WallConfig newWallConfig() {
        WallConfig wallConfig = new WallConfig();
        wallConfig.setSelectWhereAlwayTrueCheck(true);
        return wallConfig;
    }

    public Class<?> getObjectType() {
        return List.class;
    }
}