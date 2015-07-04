package li.mongo.driver.query;

import com.mongodb.WriteResult;
import com.unblocked.support.util.Asserts;

/**
 * WriteQuery
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月5日 上午10:12:49)
 */
public abstract class WriteQuery extends Query {
    private WriteResult writeResult;

    public WriteQuery setWriteResult(WriteResult writeResult) {
        this.writeResult = writeResult;
        return this;
    }

    public Integer getUpdateCount() {
        return Asserts.noNull(writeResult).getN();
    }
}