package summer.dao.impl;

import java.util.List;
import java.util.Map;

/**
 * @author li
 * @version 1 (2015年10月16日 下午12:16:30)
 * @since Java7
 */
public class NamedParameterParserResult {
    private String parsedSql;

    private Map<String, List<Integer>> indexMap;

    public NamedParameterParserResult(String parsedSql, Map<String, List<Integer>> indexMap) {
        this.parsedSql = parsedSql;
        this.indexMap = indexMap;
    }

    public String getParsedSql() {
        return parsedSql;
    }

    public Map<String, List<Integer>> getIndexMap() {
        return indexMap;
    }
}