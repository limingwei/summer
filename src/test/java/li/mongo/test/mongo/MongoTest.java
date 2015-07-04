package li.mongo.test.mongo;

import org.junit.Test;

import cn.limw.summer.mongo.driver.MongoConnection;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * MongoJavaDriver方式操作
 * 
 * @author li
 * @version 1 2014年3月11日上午10:07:05
 */
public class MongoTest {
    private MongoConnection getConnection() {
        return new MongoConnection("jdbc:mongo://127.0.0.1:27017/mongo_db_demo");
    }

    @Test
    public void isNull() {
        DB db = getConnection().getDb();
        DBCollection collection = db.getCollection("t_users");
        DBCursor find = collection.find().limit(5);
        while (find.hasNext()) {
            DBObject object = find.next();
            System.err.println(object);
        }
    }

    @Test
    public void listFromEmptyTable() {
        DB db = getConnection().getDb();
        DBCollection collection = db.getCollection("asdf");
        DBCursor find = collection.find();
        while (find.hasNext()) {
            DBObject object = find.next();
            System.err.println(object);
        }
    }
}