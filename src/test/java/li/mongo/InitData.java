package li.mongo;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * InitData
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月5日 上午11:50:11)
 */
public class InitData extends BaseTest {
    @Test
    public void initData() throws Throwable {
        Mongo mongo = new MongoClient();
        DB db = mongo.getDB("mongo_db_demo");
        DBCollection collection = db.getCollection("t_users");
        for (int i = 0; i < 10000; i++) {
            DBObject object = new BasicDBObject();
            object.put("id", i);
            object.put("username", "username-" + i);
            System.err.println(collection.insert(object));
        }
    }
}