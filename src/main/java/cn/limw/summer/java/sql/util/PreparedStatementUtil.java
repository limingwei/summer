package cn.limw.summer.java.sql.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Nums;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年2月25日 上午10:35:56)
 * @since Java7
 */
public class PreparedStatementUtil {
    private static final Logger log = Logs.slf4j();

    public static Integer getGeneratedKey(PreparedStatement preparedStatement) {
        try {
            if (null != preparedStatement) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (null != generatedKeys) {
                    if (generatedKeys.next()) {
                        return Nums.toInt(generatedKeys.getObject(1));
                    }
                }
            }

            return null;
        } catch (Exception e) {
            if (e instanceof SQLException) {
                String message = e.getMessage();
                if (StringUtil.containsIgnoreCase(message, "You need to specify Statement.RETURN_GENERATED_KEYS to")) {
                    log.warn("" + e.getMessage());
                    return null;
                }
            }
            throw new RuntimeException(e);
        }
    }
}