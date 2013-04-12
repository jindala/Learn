package Modal;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class DBUtil {
	
	static final String DB_NAME = "socialfood";

	public String insertintoDB(String collectionName, BasicDBObject doc) throws UnknownHostException
	{
		DBCollection coll = getCollection(collectionName);
		coll.insert(doc);
		ObjectId id = (ObjectId)doc.get( "_id" );
		return id.toString();
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
	
	public JSONObject queryDocs(String collectionName,BasicDBObject basicdbObject) throws UnknownHostException
	{
		DBCollection coll = getCollection(collectionName);
		DBCursor cursor = coll.find(basicdbObject);
		List<DBObject> dbObjectList = cursor.toArray();
		try {
			JSONObject resultJSON = new JSONObject();
			JSONArray resultArray = new JSONArray();
			
            for(DBObject dbObject: dbObjectList) {
                ObjectId id = (ObjectId)dbObject.get("_id");
            	Map resultMap = dbObject.toMap();
                resultMap.remove("_id");
                resultMap.put("unique_id", id.toString());
                resultArray.add(resultMap);
            }
            resultJSON.put("result", resultArray);
            System.out.println("result: "+ resultArray);
            //return JSONValue.toJSONString(resultJSON);
            return resultJSON;
        } finally {
            cursor.close();
        }
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
		System.out.println("got db " + db.getName() );
		DBCollection coll = db.getCollection(collectionName);
		System.out.println("does collection exists?" + db.collectionExists(collectionName));
		return coll;
	}
}
