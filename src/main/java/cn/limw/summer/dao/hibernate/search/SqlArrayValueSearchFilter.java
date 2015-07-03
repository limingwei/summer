package cn.limw.summer.dao.hibernate.search;

import java.util.Map.Entry;

import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.StringUtil;

import com.alibaba.fastjson.JSONArray;

/**
 * @author li
 * @version 1 (2015年1月21日 下午5:38:01)
 * @since Java7
 */
public class SqlArrayValueSearchFilter extends SearchFilter {
    private String defaultValue = "'SqlArrayValueSearchFilter.defaultValue'";

    public String getDefaultValue() {
        return Asserts.noNull(defaultValue, "defaultValue 不可以为空");
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getSqlArrayValue(SearchCriteria searchCriteria, JSONArray array) {
        String sqlArrayValue;
        int size = array.size();
        if (size > 0) {
            String[] items = new String[size];
            for (int i = 0; i < size; i++) {
                items[i] = ":" + parameter(searchCriteria, array.get(i));
            }
            sqlArrayValue = StringUtil.join(", ", items);
        } else {
            sqlArrayValue = getDefaultValue();
        }
        return sqlArrayValue;
    }

    public String getSqlArrayValue(SearchCriteria searchCriteria, Entry node) {
        return getSqlArrayValue(searchCriteria, (JSONArray) node.getValue());
    }
}