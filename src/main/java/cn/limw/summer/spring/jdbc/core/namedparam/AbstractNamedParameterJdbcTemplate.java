package cn.limw.summer.spring.jdbc.core.namedparam;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author li
 * @version 1 (2015年6月3日 下午2:17:09)
 * @since Java7
 */
public class AbstractNamedParameterJdbcTemplate extends NamedParameterJdbcTemplate {
    public AbstractNamedParameterJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }
}