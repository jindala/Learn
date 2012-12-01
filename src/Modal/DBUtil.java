package Modal;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class DBUtil {
	
	static final String DB_NAME = "myLittleRestaurant";

	public boolean insertintoDB(String collectionName, BasicDBObject doc) throws UnknownHostException
	{
		DBCollection coll = getCollection(collectionName);
		coll.insert(doc);
		return true;
	}
	
	public Map getAllDocs(String collectionName) throws UnknownHostException
	{
		DBCollection coll = getCollection(collectionName);
		DBCursor cursor = coll.find();
		List<DBObject> dbObjectList = cursor.toArray();
		try {
            for(DBObject dbObject: dbObjectList) {
                Map resultMap = dbObject.toMap();
                System.out.println("full map " + resultMap);
                return resultMap;
            }
        } finally {
            cursor.close();
        }
		
		return null;
	}
	
	public Map queryDocs(String collectionName,BasicDBObject basicdbObject) throws UnknownHostException
	{
		DBCollection coll = getCollection(collectionName);
		DBCursor cursor = coll.find(basicdbObject);
		List<DBObject> dbObjectList = cursor.toArray();
		try {
            for(DBObject dbObject: dbObjectList) {
                Map resultMap = dbObject.toMap();
                System.out.println("full map " + resultMap);
                return resultMap;
            }
        } finally {
            cursor.close();
        }
		
		return null;
	}
	
	private DBCollection getCollection(String collectionName) throws UnknownHostException
	{
		Mongo m = null;
		try {
			m = new Mongo("localhost" , 27017);
		} catch (UnknownHostException e) {
			System.out.println("Could not connecto to Mongo DB");
			throw e;
		}
		
		DB db = m.getDB(DB_NAME);
		DBCollection coll = db.getCollection(collectionName);
		
		return coll;
	}
}
