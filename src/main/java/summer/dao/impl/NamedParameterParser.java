package summer.dao.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import summer.log.Logger;
import summer.util.Log;

/**
 * @author li
 * @version 1 (2015年10月16日 下午12:06:54)
 * @since Java7
 */
public class NamedParameterParser {
    private static final Logger log = Log.slf4j();

    private Map<String, NamedParameterParserResult> cacheMap = new HashMap<String, NamedParameterParserResult>();

    /**
     * @param sqlSource
     * @param paramMap
     */
    public NamedParameterParserResult parseSql(String sqlSource) {
        NamedParameterParserResult result = cacheMap.get(sqlSource);
        if (null == result) {
            cacheMap.put(sqlSource, result = doParseSql(sqlSource));
        }
        return result;
    }

    /**
     * @param sqlSource
     * @param indexMap
     */
    private NamedParameterParserResult doParseSql(String sqlSource) {
        Map<String, List<Integer>> indexMap = new HashMap<String, List<Integer>>();
        int length = sqlSource.length();
        StringBuffer parsedSql = new StringBuffer(length);
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        int index = 1;

        for (int i = 0; i < length; i++) {
            char c = sqlSource.charAt(i);
            if (inSingleQuote) {
                if (c == '\'') {
                    inSingleQuote = false;
                }
            } else if (inDoubleQuote) {
                if (c == '"') {
                    inDoubleQuote = false;
                }
            } else {
                if (c == '\'') {
                    inSingleQuote = true;
                } else if (c == '"') {
                    inDoubleQuote = true;
                } else if (c == ':' && i + 1 < length && Character.isJavaIdentifierStart(sqlSource.charAt(i + 1))) {
                    int j = i + 2;
                    while (j < length && Character.isJavaIdentifierPart(sqlSource.charAt(j))) {
                        j++;
                    }
                    String name = sqlSource.substring(i + 1, j);
                    c = '?'; // replace the parameter with a question mark
                    i += name.length(); // skip past the end if the parameter

                    List<Integer> indexList = indexMap.get(name);
                    if (indexList == null) {
                        indexList = new LinkedList<Integer>();
                        indexMap.put(name, indexList);
                    }
                    indexList.add(new Integer(index));

                    index++;
                }
            }
            parsedSql.append(c);
        }

        log.info("sqlSource={}, parsedSql={}, paramMap={}", sqlSource, parsedSql, indexMap);
        return new NamedParameterParserResult(parsedSql.toString(), indexMap);
    }
}