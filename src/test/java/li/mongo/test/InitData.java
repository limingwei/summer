package li.mongo.test;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

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
            collection.insert(object);
        }
    }
}