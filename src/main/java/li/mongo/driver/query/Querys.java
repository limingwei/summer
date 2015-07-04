package li.mongo.driver.query;

import java.io.StringReader;

import li.mongo.driver.MongoPreparedStatement;
import li.mongo.driver.query.impl.DeleteQuery;
import li.mongo.driver.query.impl.InsertQuery;
import li.mongo.driver.query.impl.SelectQuery;
import li.mongo.driver.query.impl.UpdateQuery;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import com.unblocked.support.util.Errors;

/**
 * Querys
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午2:57:47)
 */
public class Querys {
    private static final CCJSqlParserManager SQL_PARSER_MANAGER = new CCJSqlParserManager();

    public static Query of(String sql, MongoPreparedStatement preparedStatement) {
        try {
            Statement statement = SQL_PARSER_MANAGER.parse(new StringReader(sql));
            if (statement instanceof Select) {
                SelectQuery query = new SelectQuery();
                query.setPreparedStatement(preparedStatement);
                query.setSql(sql);
                query.setStatement(statement);
                PlainSelect plainSelect = (PlainSelect) ((Select) statement).getSelectBody();
                query.setTable(plainSelect.getFromItem() + "");
                Limit limit = plainSelect.getLimit();
                if (null != limit) {
                    query.setSkip(limit.getOffset());
                    query.setLimit(limit.getRowCount());
                }
                return query;
            } else if (statement instanceof Insert) {
                InsertQuery query = new InsertQuery();
                query.setPreparedStatement(preparedStatement);
                query.setSql(sql);
                return query;
            } else if (statement instanceof Delete) {
                DeleteQuery query = new DeleteQuery();
                query.setPreparedStatement(preparedStatement);
                query.setSql(sql);
                return query;
            } else if (statement instanceof Update) {
                UpdateQuery query = new UpdateQuery();
                query.setPreparedStatement(preparedStatement);
                query.setSql(sql);
                return query;
            } else {
                throw Errors.wrap("error query " + sql);
            }
        } catch (JSQLParserException e) {
            throw Errors.wrap(e);
        }
    }
}